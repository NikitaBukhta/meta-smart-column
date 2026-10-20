SUMMARY = "Smart Column application stack"
inherit packagegroup

RDEPENDS:${PN} = " \
    smart-column-platform \
    pipewire \
    pipewire-tools \
    alsa-utils \
"
