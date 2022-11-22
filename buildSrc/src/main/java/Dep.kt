object Versions {
    const val buildTools = "30.0.3"
    const val compileSdk = 31

    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Runner {
    const val instrument = "androidx.test.runner.AndroidJUnitRunner"
}

object Dep {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.2.2"
    const val material = "com.google.android.material:material:1.5.0"

    object Kotlin {
        private const val version = "1.6.21"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"

        object Coroutine {
            private const val version = "1.5.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {

        object Lifecycle {
            private const val lifecycleVersion = "2.5.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        }

        const val activity = "androidx.activity:activity-ktx:1.3.1"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.6"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        object Room {
            private const val version = "2.3.0"

            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val test = "androidx.room:room-testing:$version"
        }

        object Test {
            const val androidXTestCore = "androidx.test:core:1.3.0"
            const val fragmentTest = "androidx.fragment:fragment-testing:1.4.1"
        }
    }

    object Square {
        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"

        object OkHttp {
            const val bom = "com.squareup.okhttp3:okhttp-bom:4.10.0"
            const val interceptor = "com.squareup.okhttp3:logging-interceptor"
        }
    }

    object Koin {
        private const val version = "3.1.5"

        const val android = "io.insert-koin:koin-android:$version"
        const val core = "io.insert-koin:koin-core:$version"

        object Test {
            const val test = "io.insert-koin:koin-test:$version"
            const val testJunit4 = "io.insert-koin:koin-test-junit4:$version"
        }
    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val glideCompiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Logger {
        private const val version = "2.2.0"
        const val logger = "com.orhanobut:logger:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"

        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"

        const val json = "org.json:json:20210307"
        const val archTest = "androidx.arch.core:core-testing:2.1.0"
    }

    const val ksp = "com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.4"
    const val naverMap = "com.naver.maps:map-sdk:3.16.0"
    const val readMoreTextView = "kr.co.prnd:readmore-textview:1.0.0"
    const val clustering = "io.github.ParkSangGwon:tedclustering-naver:1.0.2"
    const val lottie = "com.airbnb.android:lottie:5.0.3"
    const val gson = "com.google.code.gson:gson:2.8.7"
    const val kakaoUser = "com.kakao.sdk:v2-user:2.11.2"
}
