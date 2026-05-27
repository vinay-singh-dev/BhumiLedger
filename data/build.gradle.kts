plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

val cloudinaryCloudName =
    rootProject.file("local.properties")
        .readLines()
        .find { it.startsWith("CLOUDINARY_CLOUD_NAME=") }
        ?.substringAfter("=")
        ?: ""

val cloudinaryApiKey =
    rootProject.file("local.properties")
        .readLines()
        .find { it.startsWith("CLOUDINARY_API_KEY=") }
        ?.substringAfter("=")
        ?: ""

val cloudinaryApiSecret =
    rootProject.file("local.properties")
        .readLines()
        .find { it.startsWith("CLOUDINARY_API_SECRET=") }
        ?.substringAfter("=")
        ?: ""

android {

    namespace = "com.example.bhumiledger.data"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {

        minSdk = 27

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "CLOUDINARY_CLOUD_NAME",
            "\"$cloudinaryCloudName\""
        )

        buildConfigField(
            "String",
            "CLOUDINARY_API_KEY",
            "\"$cloudinaryApiKey\""
        )

        buildConfigField(
            "String",
            "CLOUDINARY_API_SECRET",
            "\"$cloudinaryApiSecret\""
        )
    }

    buildTypes {

        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {

        compilerOptions {

            jvmTarget =
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }
}

dependencies {

    implementation(
        platform(
            "com.google.firebase:firebase-bom:34.12.0"
        )
    )

    implementation(
        "com.google.firebase:firebase-firestore"
    )

    implementation(
        "com.cloudinary:cloudinary-android:3.0.2"
    )

    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3"
    )

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(project(":domain"))

    // Room
    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}