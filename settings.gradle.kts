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
include(":core:ui-theme")
include(":core:datastore")
include(":feature:auth")
include(":data:auth")
