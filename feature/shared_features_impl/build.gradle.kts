plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)

            implementation(libs.bundles.ktor)
            implementation(libs.room.runtime)
            implementation(project(":feature:shared_features_api"))
            implementation(project(":core:ui-common"))
            implementation(project(":core:common"))
            implementation(project(":core:network"))
            implementation(project(":core:database"))
            implementation(project(":orbit_mvi"))
        }
    }
}