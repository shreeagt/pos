package com.pos.build

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


object BuildPlugins {
    const val androidApp ="com.android.application"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"

}

/**
 * To define dependencies
 */
object Deps {

    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycleKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}" }

    // compose
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val viewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1" }
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNav}" }

    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose}" }
    val composeToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose}" }
    val composeUiTestJunit by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose}" }
    val composeToolingDebug by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }
    val composeDebugUiTestMainfest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose}" }


    // compose Bom
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val bomMaterial3 by lazy { "androidx.compose.material3:material3" }
    val bomMaterial2 by lazy { "androidx.compose.material:material" }
    val bomPreView by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val bomTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val bomUiTestJunit by lazy { "androidx.compose.ui:ui-test-junit4" }
    val bomUiTestMainFest by lazy { "androidx.compose.ui:ui-test-manifest" }


    //material
    val composeMaterial3 by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val composeMaterial2 by lazy { "androidx.compose.material:material:${Versions.material2}" }


    //Android X
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }


    // lifecycle
    val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}" }
        //compose viewmodel
    val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleKtx}" }
    val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleKtx}" }
    val composeLifecycleUtil by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleKtx}" }
    val saveDataToViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleKtx}" }
    val kaptAnotaton by lazy { "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleKtx}"}
    val lifecycleService by lazy { "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleKtx}" }
    val applifecycleProcess by lazy { "androidx.lifecycle:lifecycle-process:${Versions.lifecycleKtx}" }


    // coroutines
    val coroutunes by lazy {    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"  }

    // Retrofit
    val retrofit  by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val gsonConverter  by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val gson  by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val moshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val moshi  by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val sandwich by lazy {"com.github.skydoves:sandwich:${Versions.sandwich}"}

    //hit
    val  hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltAndoidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    val hiltJetpackNavigation by lazy { "androidx.hilt:hilt-navigation-fragment:1.0.0" }

    val hiltWorkManager by lazy {"androidx.hilt:hilt-work:1.0.0"}
    val hiltWorkManagerCompilerKapt by lazy { "androidx.hilt:hilt-compiler:1.0.0" }

    //coil
    val coil by lazy { "io.coil-kt:coil:2.3.0" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:2.3.0" }



    // Test
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val espresso  by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val exJunit  by lazy { "androidx.test.ext:junit-ktx:${Versions.extJunit}" }


}


fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.plateform(name: String): Dependency {
    return platform(name)
}

fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.debugImplementation(names: List<String>) {
    names.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}