require linux-intel-acrn_5.4.inc

KBRANCH = "release_2.2"

SRC_URI_remove = "git://github.com/intel/linux-intel-lts.git;protocol=https;name=machine;branch=${KBRANCH};"
SRC_URI_prepend = "git://github.com/projectacrn/acrn-kernel.git;protocol=https;name=machine;branch=${KBRANCH};"

#SRC_URI_remove = "file://0001-Add-the-plane-restrictionfor-SKL.-Otherwise-there-is.patch"
#SRC_URI_remove = "file://0002-Add-the-change-for-gvt-g-on-SKL.patch"

# tag: v2.2
LINUX_VERSION = "5.4.59"
SRCREV_machine = "38b218fabf1f745acf7eae35f152faee69c1fd8f"
