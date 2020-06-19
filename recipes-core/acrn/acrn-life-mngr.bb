require acrn-common.inc

do_compile() {
	oe_runmake -C misc/life_mngr
}

do_install() {
	install -d ${D}${libdir}/systemd/system
	oe_runmake -C misc/life_mngr install
}

SYSTEMD_SERVICE_${PN} = "life_mngr.service"

FILES_${PN} += " ${libdir}/systemd/system "
