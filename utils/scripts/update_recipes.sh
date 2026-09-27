#!/bin/bash

# Upgrades the recipe of every repository below to the head of its default
# branch and writes it into this layer. The recipe is named after the
# repository. Run it from a shell that has sourced the build's init-build-env.

REPOS=(
    ssh://git@github.com/NikitaBukhta/smart-column-platform.git
)

LAYER_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/../.." && pwd)"

[ -n "${BUILDDIR}" ] || {
    echo "ERROR: no build environment, source init-build-env first" >&2
    exit 1
}

for repo in "${REPOS[@]}"; do
    recipe="$(basename "${repo}" .git)"
    rev="$(git ls-remote "${repo}" HEAD | cut -f1)"
    echo "==> ${recipe}: ${rev}"

    devtool upgrade --srcrev "${rev}" "${recipe}" &&
        devtool finish --remove-work "${recipe}" "${LAYER_DIR}" ||
        devtool reset --remove-work "${recipe}" 2>/dev/null
done
