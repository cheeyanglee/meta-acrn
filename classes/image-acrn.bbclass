IMAGE_FSTYPES += "ext4 wic"

inherit acrn-bootconf

WICVARS_append = " ACRN_EFI_BOOT_CONF IMAGE_EFI_BOOT_FILES "
