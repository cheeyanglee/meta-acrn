require recipes-kernel/linux/linux-intel-ese-lts-rt-5.4_git.bb

FILESEXTRAPATHS_prepend := "${LAYERDIR-ese-bsp}/recipes-kernel/linux/linux-config:${LAYERDIR-ese-bsp}/recipes-kernel/linux/files:${LAYERDIR-acrn}/recipes-kernel/linux/files:"

KERNEL_SRC_URI = "git://github.com/intel/linux-intel-lts.git;protocol=https;branch=5.4/preempt-rt;name=machine"

SRC_URI_append = "  file://uos_rt_5.4.scc"

LINUX_VERSION_EXTENSION = "-linux-intel-ese-lts-preempt-rt-acrn-uos"

SUMMARY = "Linux Intel ESE Preempt RT Kernel with ACRN enabled (UOS)"

KERNEL_VERSION_SANITY_SKIP = "1"
KCONF_BSP_AUDIT_LEVEL = "0"
