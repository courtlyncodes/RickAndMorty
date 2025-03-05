plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.rickmorty"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickmorty"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Retrofit
    implementation (libs.retrofit)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    implementation(libs.core)
    ksp(libs.androidx.room.compiler)

    // Moshi
    implementation(libs.moshi.kotlin)
    implementation (libs.converter.moshi)

    // Compose Material 3 Adaptive Library
    implementation(libs.adaptive)
    implementation (libs.adaptive.layout)
    implementation (libs.adaptive.navigation)

    // Http3
    implementation(libs.okhttp)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.logging.interceptor)

    // Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // Palette
    implementation(libs.androidx.palette.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}