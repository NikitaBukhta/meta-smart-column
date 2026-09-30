# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "git://git@github.com/NikitaBukhta/smart-column-platform.git;protocol=ssh;branch=master \
           file://smart-column-platform.service \
           "

# Modify these as desired
PV = "1.0+git"
SRCREV = "c802499a3af1bffcdc7080f0881eeb69900e808a"

inherit cmake systemd

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = ""
DEPENDS += " whisper-lib"

# Systemd service file for smart-column-platform
SYSTEMD_SERVICE:${PN} = "smart-column-platform.service"

do_install:append() {
    install -Dm 0644 ${UNPACKDIR}/smart-column-platform.service \
        ${D}${systemd_system_unitdir}/smart-column-platform.service
}