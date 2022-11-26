plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        testInstrumentationRunner = Runner.instrument
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    // androidx
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.material)

    // lifecycle
    implementation(Dep.AndroidX.Lifecycle.runtime)
    implementation(Dep.AndroidX.Lifecycle.liveData)
    implementation(Dep.AndroidX.Lifecycle.viewModel)

    // coroutine
    implementation(Dep.Kotlin.Coroutine.core)
    implementation(Dep.Kotlin.Coroutine.android)

    // koin
    implementation(Dep.Koin.core)
    implementation(Dep.Koin.android)

    // kakao
    implementation(Dep.kakaoUser)

    // glide
    implementation(Dep.Glide.glide)
    annotationProcessor(Dep.Glide.glideCompiler)

    // map
    implementation(Dep.naverMap)

    // read more
    implementation(Dep.readMoreTextView)

    // clustering
    implementation(Dep.clustering)

    // balloon
    implementation(Dep.balloon)

    // test
    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espresso)

}