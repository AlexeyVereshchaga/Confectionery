import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sun.confectionery.feature.auth.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
}

dependencies {
    implementation(project(":core"))
//    implementation(project(":feature-auth-domain"))
//    implementation(project(":feature-auth-data"))
//    implementation(project(":feature-auth-domainimpl"))

    implementation(libs.koin.android)
    implementation(libs.kotlinx.serialization.json)

}
