require acrn-common.inc

ACRN_BOARD ?= "nuc7i7dnb"
ACRN_FIRMWARE ?= "uefi"
ACRN_SCENARIO  ?= "sdc"
ACRN_BOARD_FILE ?= ""
ACRN_SCENARIO_FILE ?= ""

EXTRA_OEMAKE += "HV_OBJDIR=${B}/hypervisor EFI_OBJDIR=${B}/efi-stub"
EXTRA_OEMAKE += "FIRMWARE=${ACRN_FIRMWARE}"
EXTRA_OEMAKE += "${@bb.utils.contains('ACRN_BOARD_FILE', '', 'BOARD_FILE=${ACRN_BOARD_FILE}', 'BOARD=${ACRN_BOARD}', d)}"
EXTRA_OEMAKE += "${@bb.utils.contains('ACRN_SCENARIO_FILE', '', 'SCENARIO_FILE=${ACRN_SCENARIO_FILE}', 'SCENARIO=${ACRN_SCENARIO}', d)} "

inherit python3native deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "python3-kconfiglib-native"
DEPENDS += "${@'gnu-efi' if d.getVar('ACRN_FIRMWARE') == 'uefi' else ''}"

do_configure() {
	# do not override UEFI_OS_LOADER_NAME when customized scenario file selected
	if [ -z "${ACRN_SCENARIO_FILE}" ]; then
		cat <<-EOF >> ${S}/hypervisor/arch/x86/configs/${ACRN_BOARD}.config
CONFIG_UEFI_OS_LOADER_NAME="\\\\EFI\\\\BOOT\\\\bootx64.efi"
EOF
		cat ${S}/hypervisor/arch/x86/configs/${ACRN_BOARD}.config
	fi
}

do_compile() {
	oe_runmake hypervisor
	if [ "${ACRN_FIRMWARE}" = "uefi" ]; then
		oe_runmake -C misc/efi-stub
	fi
}

do_install() {
	oe_runmake -C hypervisor install install-debug
	if [ "${ACRN_FIRMWARE}" = "uefi" ]; then
		oe_runmake -C misc/efi-stub install install-debug
	fi
	# Remove sample files
	rm -rf ${D}${datadir}/acrn
	if [ "${ACRN_FIRMWARE}" = "uefi" ]; then
		rmdir --ignore-fail-on-non-empty ${D}${datadir}
	fi
}

FILES_${PN} += "${libdir}/acrn/"
FILES_${PN}-dbg += "${libdir}/acrn/*.efi.*"

addtask deploy after do_install before do_build
do_deploy() {
	install -m 0755 ${D}${libdir}/acrn/acrn.${ACRN_BOARD}.${ACRN_FIRMWARE}.${ACRN_SCENARIO}.32.out ${DEPLOYDIR}
	rm -f ${DEPLOYDIR}/acrn.32.out
	lnr ${DEPLOYDIR}/acrn.${ACRN_BOARD}.${ACRN_FIRMWARE}.${ACRN_SCENARIO}.32.out ${DEPLOYDIR}/acrn.32.out
	if [ "${ACRN_FIRMWARE}" = "uefi" ]; then
		if [ ! -z "${ACRN_SCENARIO_FILE}" ]; then
			rm -f ${DEPLOYDIR}/acrn.efi
			install -m 0755 ${B}/hypervisor/acrn.efi ${DEPLOYDIR}
		else
			install -m 0755 ${D}${libdir}/acrn/acrn.${ACRN_BOARD}.${ACRN_SCENARIO}.efi ${DEPLOYDIR}
			rm -f ${DEPLOYDIR}/acrn.efi
			lnr ${DEPLOYDIR}/acrn.${ACRN_BOARD}.${ACRN_SCENARIO}.efi ${DEPLOYDIR}/acrn.efi
		fi
	fi
}

# acrn.32.out file has elf32-i386 file format in 64-bit build, which throws Unknown file format error.
# Stripping is already done by source while building.
# So 'arch' and 'already-stripped' QA checks can be skipped.
INSANE_SKIP_${PN} += "arch already-stripped"
