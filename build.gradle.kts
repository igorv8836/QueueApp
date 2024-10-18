plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}


conventionalCommits {
    scopes = listOf(
        "main",
        "common",
        "database",
        "datastore",
        "network",
        "ui_theme",
        "auth"
    )
}
