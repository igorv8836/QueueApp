rootProject.name = "QueueApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.sellmair.io")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://repo.sellmair.io")
    }
}

plugins{
    id("org.gradle.toolchains.foojay-resolver") version "0.8.0"
}

toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass.set(org.gradle.toolchains.foojay.FoojayToolchainResolver::class.java)
            }
        }
    }
}

include(":composeApp")
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:ui-common")
include(":core:datastore")
include(":feature:auth_impl")
include(":orbit_mvi")
include(":feature:shared_features_api")
include(":feature:shared_features_impl")
include(":feature:auth_api")
include(":feature:queues_api")
include(":feature:queues_impl")
