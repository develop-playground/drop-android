plugins {
    id("kotlin")
}

dependencies {
    // koin
    implementation(Dep.Koin.core)
    //coroutine
    implementation(Dep.Kotlin.Coroutine.core)
}