plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
}

android {
  compileSdkVersion(AppConfig.compileSdk)
  buildToolsVersion = AppConfig.buildToolsVersion

  defaultConfig {
    applicationId("com.mkamilmistar.gold_market")
    minSdkVersion(26)
    targetSdkVersion(30)
    versionCode(1)
    versionName("1.0")
    kapt {
      arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
      }
    }
    javaCompileOptions {
      annotationProcessorOptions {
        arguments["room.incremental"] = "true"
      }
    }
    testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
  }

  buildFeatures{
    dataBinding = true
    viewBinding = true
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
  implementation(AppDependencies.coroutines)
  implementation(AppDependencies.lifecycleViewModel)
  implementation(AppDependencies.room)
  kapt(AppDependencies.roomCompiler)
  implementation(AppDependencies.material)
  implementation(AppDependencies.constraintLayout)
  implementation(AppDependencies.androidFragment)
  implementation(AppDependencies.navigationFragment)
  implementation(AppDependencies.navigationAndroid)
  testImplementation(AppDependencies.junit)
  androidTestImplementation(AppDependencies.extJunit)
  androidTestImplementation(AppDependencies.espressoCore)
}
