package com.pos.build

object Configuration{
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val majorVersion = 1
    const val minorVersion = 1
    const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
    const val buildToolsVersion = "29.0.3"
    const val jvmTarget = "17"

    const val kotlinCompiler = "1.4.4"
    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules =  "consumer-rules.pro"
    const val dimension = "environment"
}

