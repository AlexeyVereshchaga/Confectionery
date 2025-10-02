import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:auth:data"))
    implementation(project(":features:auth:domain"))
    implementation(project(":features:auth:domainimpl"))

    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.coil.compose)

    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}

detekt {
    config.setFrom(files("$rootDir/detekt.yml"))
    buildUponDefaultConfig = true
    reports {
        html.required.set(true)
    }
}
