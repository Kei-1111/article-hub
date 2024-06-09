// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Secrets
buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // Ksp
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.49" apply false
}