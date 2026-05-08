
plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")


}

android {
    namespace = "com.example.bhumiledger"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.bhumiledger"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }

dependencies {

//        implementation(platform("com.google.firebase:firebase-bom:34.12.0"))
//        implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")

        implementation(platform(libs.androidx.compose.bom))

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)

        // Compose (ONLY these, BOM handles versions)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.material3)

        debugImplementation(libs.androidx.compose.ui.tooling)

        implementation(project(":data"))
        implementation(project(":domain"))

        implementation("androidx.navigation:navigation-compose:2.7.7")
        implementation(libs.androidx.work.runtime.ktx)

//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    }
}



