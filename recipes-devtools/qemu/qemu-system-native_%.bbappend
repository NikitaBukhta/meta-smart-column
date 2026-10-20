# libpipewire looks for its plugins in pipewire-native's own sysroot, not in the
# one runqemu starts QEMU from: "Could not create PipeWire loop"
do_install:append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'pipewire', 'true', 'false', d)}; then
        for qemu in ${D}${bindir}/qemu-system-*; do
            create_wrapper ${qemu} \
                SPA_PLUGIN_DIR=${libdir}/spa-0.2 \
                PIPEWIRE_MODULE_DIR=${libdir}/pipewire-0.3 \
                PIPEWIRE_CONFIG_DIR=${datadir}/pipewire
        done
    fi
}
