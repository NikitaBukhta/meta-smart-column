#!/bin/bash

# Upgrades every git recipe of this layer (recipes-*/**/*_git.bb) to the tag
# matching its PV or, when there is no such tag, to the head of the branch
# named in its SRC_URI (the default branch when none is given), and writes it
# back into this layer. Run it from a shell that has sourced the build's
# init-build-env.

LAYER_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../.." && pwd)"

[ -n "${BUILDDIR}" ] || {
    echo "ERROR: no build environment, source init-build-env first" >&2
    exit 1
}

for bb in "${LAYER_DIR}"/recipes-*/*/*_git.bb; do
    recipe="$(basename "${bb}" _git.bb)"

    # git://host/path;protocol=ssh;branch=master -> ssh://host/path
    src="$(grep -o 'git://[^ "\\]*' "${bb}" | head -n1)"
    [ -n "${src}" ] || continue
    protocol="$(grep -o ';protocol=[^;]*' <<< "${src}" | cut -d= -f2)"
    branch="$(grep -o ';branch=[^;]*' <<< "${src}" | cut -d= -f2)"
    repo="${protocol:-git}://${src#git://}"
    repo="${repo%%;*}"

    # PV = "v1.9.4+git" -> tag v1.9.4 or 1.9.4; the commit of an annotated
    # tag (^{}) wins over the tag object itself
    pv="$(sed -nE 's/^PV[ ?:]*= *"([^"+]*).*/\1/p' "${bb}" | head -n1)"
    rev=""
    if [ -n "${pv}" ]; then
        patterns=()
        for tag in "${pv}" "v${pv#v}" "${pv#v}"; do
            patterns+=("refs/tags/${tag}" "refs/tags/${tag}^{}")
        done
        tags="$(git ls-remote "${repo}" "${patterns[@]}")"
        line="$(grep '\^{}$' <<< "${tags}" | head -n1)"
        [ -n "${line}" ] || line="$(head -n1 <<< "${tags}")"
        rev="$(cut -f1 <<< "${line}")"
    fi
    if [ -n "${rev}" ]; then
        ref="$(cut -f2 <<< "${line}")"
        ref="${ref#refs/tags/}"
        ref="tag ${ref%^\{\}}"
    else
        ref="${branch:-HEAD}"
        rev="$(git ls-remote "${repo}" "${branch:+refs/heads/}${branch:-HEAD}" | cut -f1)"
    fi
    [ -n "${rev}" ] || {
        echo "ERROR: ${recipe}: cannot resolve ${ref} of ${repo}" >&2
        continue
    }
    echo "==> ${recipe}: ${rev} (${ref})"

    devtool upgrade --srcrev "${rev}" "${recipe}" &&
        devtool finish --remove-work "${recipe}" "${LAYER_DIR}" ||
        devtool reset --remove-work "${recipe}" 2>/dev/null
done
