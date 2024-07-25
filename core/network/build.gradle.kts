plugins {
    alias(libs.plugins.myKotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}