plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.orbit.core)


            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            implementation(libs.napier)

            implementation(project(":core:common"))
            implementation(project(":core:ui-theme"))
            implementation(project(":data:auth"))
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