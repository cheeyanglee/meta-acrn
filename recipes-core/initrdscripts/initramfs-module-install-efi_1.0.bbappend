FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " ${@bb.utils.contains('DISTRO_FEATURES', 'SOS', 'file://acrn-init-install-efi.sh', '', d)} "

do_install_append() {
    # override ${D}/init.d/install-efi.sh
    if [ -f ${WORKDIR}/acrn-init-install-efi.sh ]; then
        install -m 0755 ${WORKDIR}/acrn-init-install-efi.sh ${D}/init.d/install-efi.sh
    fi
}
