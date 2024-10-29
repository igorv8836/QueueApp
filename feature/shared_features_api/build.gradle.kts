plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.room.runtime)

            implementation(project(":core:common"))
            implementation(project(":core:database"))
            implementation(project(":core:ui-common"))
        }
    }
}