object Dependencies {
  const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
  const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
  const val DAGGER_ANDROID = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
  const val DAGGER_ANDROID_PROCESSOR = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"

  const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
  const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"

  const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
  const val MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.MOSHI}"

  const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VIEWMODEL}"
  const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_LIVEDATA}"

  const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"

  const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
  const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
  const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
  const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
  const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
  const val JUNIT = "junit:junit:${Versions.JUNIT}"
  const val JUNIT_ANDROID = "androidx.test.ext:junit:${Versions.JUNIT_ANDROID}"
  const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"

  const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}"
  const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
  const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

  const val SECURITY_CRYPTO = "androidx.security:security-crypto:${Versions.CRYPTO}"

  const val ANDROID_GRADLE_TOOL = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_TOOL}"
  const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
}
