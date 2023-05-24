package com.pos.build

import org.gradle.api.artifacts.dsl.DependencyHandler


object Libs {

    fun DependencyHandler.ComposeWithBom() {
        val implement = listOf(
            Deps.bomMaterial3,
            Deps.bomPreView,
            Deps.activityCompose,
            Deps.viewModelCompose,
            Deps.coilCompose
        )
        val debugImpl = listOf(
            Deps.bomTooling,
            Deps.bomUiTestMainFest
        )
        val androidTestImpl = listOf(
            Deps.bomUiTestJunit
        )
        val composeBom = platform(Deps.composeBom)
        implementation(composeBom)
        implementation(implement)
        debugImplementation(debugImpl)
        androidTestImplementation(composeBom)
        androidTestImplementation(androidTestImpl)
    }

    fun DependencyHandler.Retrofit() {
        val imp = listOf(
            Deps.retrofit,
            Deps.gsonConverter,
            Deps.gson,
            Deps.moshiConverter,
            Deps.moshi,
            Deps.sandwich
        )

        implementation(imp)
    }

    fun DependencyHandler.Lifecycle() {
        val imp = listOf(
            Deps.viewModel,
            Deps.composeViewModel,
            Deps.liveData,
            Deps.composeLifecycleUtil,
            Deps.saveDataToViewModel,
            Deps.lifecycleService,
            Deps.applifecycleProcess
        )
        implementation(imp)
        kapt(listOf(Deps.kaptAnotaton))
    }

    fun DependencyHandler.TestUnit() {
        val test = listOf(
            Deps.junit,
        )
        val androidTest = listOf(
            Deps.espresso,
            Deps.exJunit
        )
        testImplementation(test)
        androidTestImplementation(androidTest)

    }

    fun DependencyHandler.AndroidX() {
        val iml = listOf(
            Deps.coreKtx,
            Deps.lifecycleKtx,
            Deps.appCompat,
            Deps.constraintLayout
        )
        implementation(iml)
    }

    fun DependencyHandler.Corotuin() {
        val imp = listOf(
            Deps.coroutunes
        )
        implementation(imp)
    }

    fun DependencyHandler.Debug(){
        implementation(Deps.timber)
    }


}