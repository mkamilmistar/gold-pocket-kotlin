import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
}

android {
  compileSdkVersion(Versions.COMPILE_SDK_VERSION)
  buildToolsVersion(Versions.BUILD_TOOL_VERSION)
  val url: String = gradleLocalProperties(rootDir).getProperty("goldMarket_api")

  defaultConfig {
    applicationId("com.mkamilmistar.gold_market")
    minSdkVersion(Versions.MIN_SDK_VERSION)
    targetSdkVersion(Versions.TARGET_SDK_VERSION)
    versionCode(Versions.VERSION_CODE)
    versionName = Versions.VERSION_NAME
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

    buildConfigField("String", "BASE_URL", url)
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
    jvmTarget = Versions.JVM_TARGET
  }
}

dependencies {
  dagger()
  daggerAndroid()
  room()
  retrofit()
  coroutine()
  lifecycle()
  core()
  navigation()
  ui()
  unitTest()
  androidUnitTest()
  security()
}
