plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            implementation(project(":core:common"))
            implementation(project(":core:datastore"))
            implementation(project(":core:network"))
            implementation(project(":core:ui-theme"))
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