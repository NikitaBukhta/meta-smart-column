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

SM_DAEMONS = "sm-audio-managerd sm-dispatcherd sm-keyword-catcherd sm-llmd sm-searchd sm-sttd sm-ttsd"
SRC_URI = "git://git@github.com/NikitaBukhta/smart-column-platform.git;protocol=ssh;branch=master \
           file://sm-audio-managerd.service \
           file://sm-dispatcherd.service \
           file://sm-keyword-catcherd.service \
           file://sm-llmd.service \
           file://sm-searchd.service \
           file://sm-sttd.service \
           file://sm-ttsd.service \
           "

# Modify these as desired
PV = "1.0+git"
SRCREV = "1d1e5f4411aeb8f18ab30680cc1e360a2611f132"

inherit cmake systemd

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = ""
DEPENDS += " whisper-lib"

# Systemd service file for smart-column-platform
SYSTEMD_SERVICE:${PN} = "${@' '.join(d + '.service' for d in d.getVar('SM_DAEMONS').split())}"
do_install:append() {
    for daemon in ${SM_DAEMONS}; do
        install -Dm 0644 ${UNPACKDIR}/${daemon}.service \
            ${D}${systemd_system_unitdir}/${daemon}.service
    done
}