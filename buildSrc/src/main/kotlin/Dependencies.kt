object Config {
    val compileSdk = 29
    val buildTools = "29.0.3"
    val applicationId = "info.covid"
    val minSdk = 21
    val targetSdk = 29
    val versionCode = 20
    val versionName = "1.6.2"
}


object Versions {
    const val kotlin_stdlib = "1.3.61"
    const val material = "1.2.0-alpha05"

    const val constraintLayout = "2.0.0-beta4"
    const val retrofit = "2.7.1"
    const val moshi = "2.6.0"

    const val fragment_ktx = "1.2.4"
    const val liveData = "2.2.0"

    const val dime = "1.0.6"

    const val lifecycle = "2.1.0"
    const val MPAndroidChart = "v3.1.0"
    const val room = "2.2.5"

    const val nav_version = "2.2.1"

    const val preference = "1.1.0"
    const val koin = "2.0.1"
    const val bugsnag = "4.11.0"

    const val espresso = "3.2.0"
    const val test_ext = "1.1.1"
    const val junit = "4.12"
}

object Deps {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib}"

    val material = "com.google.android.material:material:${Versions.material}"

    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.moshi}"


    val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"

    val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"

    val MPAndroidChart = "com.github.PhilJay:MPAndroidChart:${Versions.MPAndroidChart}"

    val sdp = "com.intuit.sdp:sdp-android:${Versions.dime}"
    val ssp = "com.intuit.ssp:ssp-android:${Versions.dime}"

    val room = "androidx.room:room-runtime:${Versions.room}"
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    val preference = "androidx.preference:preference:${Versions.preference}"
    val koin = "org.koin:koin-android-viewmodel:${Versions.koin}"

    val junit = "junit:junit:${Versions.junit}"
    val test_ext = "androidx.test.ext:junit:${Versions.test_ext}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

}