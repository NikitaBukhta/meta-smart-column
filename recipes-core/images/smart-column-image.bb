SUMMARY = "Smart Column image"
require recipes-core/images/core-image-minimal.bb
IMAGE_INSTALL:append = " packagegroup-smart-column"

# append external libs
TOOLCHAIN_TARGET_TASK:append = " whisper-lib-dev"

# Keep the proprietary application's -dev, -dbg and -src out of the public SDK
PACKAGE_EXCLUDE_COMPLEMENTARY = "smart-column-platform"