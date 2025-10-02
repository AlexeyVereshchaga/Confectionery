import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sun.confectionery.feature.products.list.presentation"
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
    implementation(project(":features:products:domain"))
    implementation(project(":features:products:data"))
    implementation(project(":features:products:domainimpl"))

    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.coil.compose)
    implementation(libs.material.icons.extended)

    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
