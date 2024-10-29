plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor)
            implementation(project(":core:common"))
            implementation(project(":core:network"))
            implementation(project(":core:database"))
            implementation(project(":core:datastore"))
            implementation(project(":core:ui-common"))
            implementation(project(":feature:auth_api"))
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