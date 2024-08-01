plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  // alias(libs.plugins.android.hilt)
  // alias(libs.plugins.kotlin.kapt)
  //id("kotlin-kapt")
  id("com.google.devtools.ksp") version "1.9.0-1.0.11"
}

android {
  namespace = "com.android.finance.helper"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.android.finance.helper"
    minSdk = 31
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
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
  implementation(libs.androidx.navigation.compose)

  // mlkit
  implementation(libs.mlkit.text.recognition)

  // coil
  implementation(libs.coil.compose)

  // camerax
  implementation(libs.camerax.core)
  implementation(libs.camerax.camera2)
  implementation(libs.camerax.lifecycle)
  implementation(libs.camerax.extensions)
  implementation(libs.camerax.video)
  implementation(libs.camerax.view)

  // hilt
  implementation(libs.hilt)
  ksp(libs.hilt.compiler)

  // room
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)

  // accompanist
  implementation(libs.accompanist)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}