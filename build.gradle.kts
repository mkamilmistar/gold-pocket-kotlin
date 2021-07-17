// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:${AppConfig.gradle_version}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${AppConfig.kotlin_version}")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle.kts files
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    jcenter() // Warning: this repository is going to shut down soon
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
