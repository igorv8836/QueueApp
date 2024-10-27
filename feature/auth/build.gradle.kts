plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            implementation(project(":core:common"))
            implementation(project(":core:ui-theme"))
            implementation(project(":data:auth"))
            implementation(project(":orbit_mvi"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {

        }

        iosMain.dependencies {

        }

        desktopMain.dependencies {

        }
    }
}