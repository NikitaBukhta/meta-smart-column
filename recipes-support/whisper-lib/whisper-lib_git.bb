# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)
SUMMARY = "whisper.cpp speech recognition library (ggml)"
HOMEPAGE = "https://github.com/ggml-org/whisper.cpp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=223b26b3c1143120c87e2b13111d3e99"

SRC_URI = "git://github.com/ggml-org/whisper.cpp.git;protocol=https;branch=master"

# Modify these as desired
PV = "1.9.4+git"
SRCREV = "927cfce34f31707e17f2bff35c349632fb9e2c3a"

# NOTE: unable to map the following CMake package dependencies: FFmpeg FlexmlRT OpenVINO llama ggml
# NOTE: the following library dependencies are unknown, ignoring: CoreML Foundation
#       (this is based on recipes that have previously been built and packaged)
# DEPENDS = "libsdl2"

inherit cmake

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
EXTRA_OECMAKE = " \
    -DWHISPER_BUILD_EXAMPLES=OFF \
    -DWHISPER_BUILD_TESTS=OFF \
    -DWHISPER_SDL2=OFF \
    -DGGML_NATIVE=OFF \
"

