plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainKotlinSerialization)
    alias(libs.plugins.devtoolKsp)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.example.fluffyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fluffyapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/v1/\"")
            buildConfigField("String", "API_KEY", "\"live_kPi2v32cRJfRpSKfBNqWwvfUU1N8K5dE70LqXiYwdPGtCHWNYzFQ8mwzDQ2hvOSG\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.retrofit.client)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.okhttp.logging)
    implementation(libs.dagger.hilt.navigation)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.ksp.compiler)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.jetbrain.serialization)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.androidx.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}