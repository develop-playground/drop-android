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
}

dependencies {
    implementation (project(":domain"))

    //android core ktx
    implementation(Dep.AndroidX.coreKtx)

    // network
    implementation(Dep.Square.retrofit)
    implementation(platform(Dep.Square.OkHttp.bom))
    implementation(Dep.Square.OkHttp.interceptor)
    implementation(Dep.gson)
    implementation(Dep.Square.gsonConverter)

    // ksp
    implementation(Dep.ksp)

    // koin
    implementation(Dep.Koin.android)
    implementation(Dep.Koin.core)

    // room
    implementation(Dep.AndroidX.Room.runtime)
    implementation(Dep.AndroidX.Room.ktx)
    implementation(Dep.AndroidX.Room.test)
}