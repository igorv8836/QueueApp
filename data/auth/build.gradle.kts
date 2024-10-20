plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.bundles.ktor)
            implementation(project(":core:network"))
            implementation(project(":core:datastore"))
            implementation(project(":core:common"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.mockk)
            implementation(libs.ktor.client.mock)
            implementation(libs.junit.jupiter)
        }
    }
}
