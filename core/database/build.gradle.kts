plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)

            implementation(project(":core:common"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

