rootProject.name = "QueueApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":composeApp")
include(":core:common")
include(":core:database")
include(":core:network")
include(":core:ui-common")
include(":core:datastore")
include(":feature:auth")
include(":data:auth")
include(":orbit_mvi")
include(":feature:shared_features_api")
include(":feature:shared_features_impl")
