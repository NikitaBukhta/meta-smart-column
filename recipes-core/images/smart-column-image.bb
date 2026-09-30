SUMMARY = "Smart Column image"
require recipes-core/images/core-image-minimal.bb
IMAGE_INSTALL:append = " packagegroup-smart-column"

# append external libs
TOOLCHAIN_TARGET_TASK:append = " whisper-lib-dev"