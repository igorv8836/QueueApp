plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.myComposeMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            implementation(libs.kotlinx.serialization.json)
            implementation("org.jetbrains.kotlinx:atomicfu:0.25.0")

            implementation(libs.navigation.compose)
            implementation(libs.orbit.core)
        }
    }
}