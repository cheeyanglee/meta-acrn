require acrn-hypervisor.bb
require acrn-common-dev.inc

ACRN_SCENARIO ?= "industry"
PROVIDES = "acrn-hypervisor"
RPROVIDES_${PN} += "acrn-hypervisor"
