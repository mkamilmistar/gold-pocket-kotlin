import org.gradle.api.artifacts.dsl.DependencyHandler

private fun DependencyHandler.implementation(depName: String) {
  add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
  add("kapt", depName)
}

private fun DependencyHandler.test(depName: String) {
  add("testImplementation", depName)
}

private fun DependencyHandler.androidTest(depName: String) {
  add("androidTestImplementation", depName)
}

fun DependencyHandler.dagger() {
  implementation(Dependencies.DAGGER)
  kapt(Dependencies.DAGGER_COMPILER)
}

fun DependencyHandler.daggerAndroid() {
  implementation(Dependencies.DAGGER_ANDROID)
  kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)
}

fun DependencyHandler.room() {
  implementation(Dependencies.ROOM)
  kapt(Dependencies.ROOM_COMPILER)
}

fun DependencyHandler.retrofit() {
  implementation(Dependencies.RETROFIT)
  implementation(Dependencies.MOSHI)
}

fun DependencyHandler.coroutine(){
  implementation(Dependencies.COROUTINE)
}

fun DependencyHandler.lifecycle(){
  implementation(Dependencies.LIFECYCLE_VIEWMODEL)
  implementation(Dependencies.LIFECYCLE_LIVEDATA)
}

fun DependencyHandler.core(){
  implementation(Dependencies.KOTLIN_STDLIB)
  implementation(Dependencies.CORE)
  implementation(Dependencies.APP_COMPAT)
}

fun DependencyHandler.ui(){
  implementation(Dependencies.MATERIAL)
  implementation(Dependencies.CONSTRAINT_LAYOUT)
}

fun DependencyHandler.security() {
  implementation(Dependencies.SECURITY_CRYPTO)
}

fun DependencyHandler.unitTest(){
  test(Dependencies.JUNIT)
}
fun DependencyHandler.androidUnitTest(){
  androidTest(Dependencies.JUNIT_ANDROID)
  androidTest(Dependencies.ESPRESSO)
}

fun DependencyHandler.navigation(){
  implementation(Dependencies.NAVIGATION_FRAGMENT)
  implementation(Dependencies.NAVIGATION_UI)
}
