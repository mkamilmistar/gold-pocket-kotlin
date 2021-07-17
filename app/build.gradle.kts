plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-android-extensions")
}

android {
  compileSdkVersion(AppConfig.compileSdk)
  buildToolsVersion = AppConfig.buildToolsVersion

  defaultConfig {
    applicationId = "com.mkamilmistar.gold_market"
    minSdkVersion(26)
    targetSdkVersion(30)
    versionCode(1)
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = AppConfig.jvmTarget
  }
}

dependencies {
  implementation(AppDependencies.kotlinStdLib)
  implementation(AppDependencies.kotlinCore)
  implementation(AppDependencies.appCompat)
  implementation(AppDependencies.material)
  implementation(AppDependencies.constraintLayout)
  implementation(AppDependencies.appCompat)
  implementation(AppDependencies.material)
  implementation(AppDependencies.kotlinStdLib)
  implementation(AppDependencies.constraintLayout)
  testImplementation(AppDependencies.junit)
  androidTestImplementation(AppDependencies.extJunit)
  androidTestImplementation(AppDependencies.espressoCore)
}
