# in format of
# <kernel image>:<VMx kern_mod>:<bootarg>;
# for each module, split each module with semicolon.
# below example show zephyr.bin as VM0 without bootargs and
# bzImage as VM1 with bootargs eg :
# ACRN_EFI_BOOT_CONF ?= "zephyr.bin:Zephyr_RawImage;bzImage:Linux_bzImage:rootwait root=/dev/sda1;"
ACRN_EFI_BOOT_CONF ??= ""

KERNEL_IMAGE ??= "${KERNEL_IMAGETYPE}"
KERNEL_MOD ??= "Linux_bzImage"
ACPI_TAG ??= ""
ACPI_BIN ??= ""


python(){
  vmflags = d.getVar("VMFLAGS") or {}

  if not vmflags:
    # default cases with only one VM
    # do not need ACPI_BIN and ACPI_TAG without two or more vm
    append = d.getVar("APPEND") or ""
    kernelimage = d.getVar("KERNEL_IMAGE") or ""
    kernelmod = d.getVar("KERNEL_MOD") or ""
    bootconf = "%s:%s:%s;" % (kernelimage,kernelmod,append)
    d.setVar('ACRN_EFI_BOOT_CONF', bootconf)

  else:
    bootconf = ""
    for flag in vmflags.split():
      append = d.getVarFlag("APPEND",flag) or ""
      kernelimage = d.getVarFlag("KERNEL_IMAGE",flag) or ""
      kernelmod = d.getVarFlag("KERNEL_MOD",flag) or ""
      acpibin = d.getVarFlag("ACPI_BIN",flag) or ""
      acpitag = d.getVarFlag("ACPI_TAG",flag) or ""

      bootconf += "%s:%s:%s;" % (kernelimage,kernelmod,append)
      if acpibin and acpitag:
        bootconf += "%s:%s;" % (kernelimage,kernelmod,append)

    d.setVar('ACRN_EFI_BOOT_CONF', bootconf)

}

