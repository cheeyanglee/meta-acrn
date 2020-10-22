require recipes-kernel/linux/linux-intel-ese-lts-5.4_git.bb

FILESEXTRAPATHS_prepend := "${LAYERDIR-ese-bsp}/recipes-kernel/linux/linux-config:${LAYERDIR-ese-bsp}/recipes-kernel/linux/files:${LAYERDIR-acrn}/recipes-kernel/linux/files:"

KERNEL_SRC_URI = "git://github.com/intel/linux-intel-lts.git;protocol=https;branch=5.4/yocto;name=machine"

KERNEL_VERSION_SANITY_SKIP = "1"
KCONF_BSP_AUDIT_LEVEL = "0"
