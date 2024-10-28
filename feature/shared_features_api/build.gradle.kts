plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui-common"))
        }
    }
}