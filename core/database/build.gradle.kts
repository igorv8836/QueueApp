import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    alias(libs.plugins.myKotlinMultiplatform)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets.commonMain{
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    sourceSets {
        androidMain.dependencies {
            project.dependencies.add("kspAndroid", libs.room.compiler)
        }
        commonMain.dependencies {
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)


            implementation(libs.koin.core)

            implementation(project(":core:common"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain{

        }

        jvmMain{

        }
    }
}
room {
    schemaDirectory("$projectDir/schemas")
}


// Проблема с KSP, необходимо добавить зависимость на kspCommonMainMetadata не для android,
// но на android данная зависимость ломает сборку приложения

val isAndroid = false

if (!isAndroid){
    dependencies.add("kspCommonMainMetadata", libs.room.compiler)
    tasks.withType<KotlinCompile<*>>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}

