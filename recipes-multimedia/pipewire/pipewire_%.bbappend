FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# No camera; libcamera pulls libpisp on Raspberry Pi, which fails to build on wrynose
PACKAGECONFIG:remove = "libcamera"

# The system-wide pipewire listens in /run/pipewire: tell every system service
SRC_URI += "file://pipewire-runtime-dir.conf"
do_install:append:class-target() {
    install -Dm 0644 ${UNPACKDIR}/pipewire-runtime-dir.conf \
        ${D}${sysconfdir}/systemd/system.conf.d/pipewire-runtime-dir.conf
}
FILES:${PN} += "${sysconfdir}/systemd/system.conf.d"
