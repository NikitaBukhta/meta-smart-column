# meta-smart-column

Yocto layer for [SmartColumn](https://github.com/NikitaBukhta/SmartColumn), a
smart speaker. It defines the image, the target machines and the recipes of
the application stack.

You don't normally clone it by hand: the SmartColumn build scripts fetch it at
the commit pinned in `build-config/smartcolumn-wrynose.conf.json`. See the
[SmartColumn README](https://github.com/NikitaBukhta/SmartColumn#readme) to
build and run the image.

The application source (`smart-column-platform`) is private, so the image
builds only with access to it. Otherwise use the prebuilt release artifacts.

## Contents

| Path | What it is |
|------|------------|
| `recipes-core/images/smart-column-image.bb` | The image: `core-image-minimal` plus the SmartColumn stack; the SDK ships `whisper-lib-dev` |
| `recipes-core/packagegroups/packagegroup-smart-column.bb` | Packages that make up the stack |
| `recipes-core/smart-column-platform/` | Main application, started by systemd (`smart-column-platform.service`) |
| `recipes-support/whisper-lib/` | [whisper.cpp](https://github.com/ggml-org/whisper.cpp) speech recognition library |
| `conf/fragments/rpi4-64.conf` | Fragment selecting the Raspberry Pi 4 (64-bit) |
| `conf/machine/qemu-rpi4-64.conf` | QEMU arm64 tuned for the Raspberry Pi 4 CPU, so both share packages and sstate |

## Dependencies

Yocto release `wrynose`:

- [openembedded-core](https://git.openembedded.org/openembedded-core)
- [meta-raspberrypi](https://git.yoctoproject.org/meta-raspberrypi) — for the Raspberry Pi 4 only

## Update recipe revisions

Moves every `*_git.bb` recipe to the tag matching its `PV`, or to its branch
head when there is no such tag. Run it from a shell with the build
environment sourced:

```bash
./utils/scripts/update_recipes.sh
```

## License

[MIT](COPYING.MIT). Provided "as is", without warranty of any kind.

Covers the layer's metadata only. Fetched sources keep their own licenses;
`smart-column-platform` is proprietary.
