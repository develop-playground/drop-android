plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

    // map
    implementation(Dep.naverMap)

    // clustering
    implementation(Dep.clustering)

    // test
    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espresso)

}