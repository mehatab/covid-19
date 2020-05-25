object Config {
    val compileSdk = 29
    val buildTools = "29.0.3"
    val applicationId = "info.covid"
    val minSdk = 21
    val targetSdk = 29
    val versionCode = 19
    val versionName = "1.5.9"
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
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.moshi}"


    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"

    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"

    const val MPAndroidChart = "com.github.PhilJay:MPAndroidChart:${Versions.MPAndroidChart}"

    const val sdp = "com.intuit.sdp:sdp-android:${Versions.dime}"
    const val ssp = "com.intuit.ssp:ssp-android:${Versions.dime}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val preference = "androidx.preference:preference:${Versions.preference}"
    val koin = "org.koin:koin-android-viewmodel:${Versions.koin}"
    val bugsnag = "com.bugsnag:bugsnag-android:${Versions.bugsnag}"

}