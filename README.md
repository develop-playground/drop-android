<div align = "center">
  <h1> drop-android </h1>
</div>

![Android Version](https://img.shields.io/badge/API-23%2B-green?style=flat&logo=Android&logoColor=white")

</br>

## 📚 Stack & Libraries
- IDE : Android Studio
- Minimum SDK level 21
- Language : [Kotlin](https://kotlinlang.org/) based + Transfer build configuration from [Groovy](https://developer.android.com/studio/build/migrate-to-kts) + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Jetpack AAC
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) : Observable data holder class.
  - [LifeCycle](https://developer.android.com/topic/libraries/architecture/lifecycle) : Use lifecycle-aware components to perform actions in response to lifecycle events such as activities and fragments(viewmodel, livedata).
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) : Manage data holder classes related to UI and performs asynchronous operations using coroutines for optimal processing.
- Test 
  - [JUnit](https://github.com/junit-team) : Simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
  - [Espresso](https://developer.android.com/training/testing/espresso) : Use Espresso to write concise, beautiful, and reliable Android UI tests.
inside the JVM on your workstation in seconds. 
- [Koin](https://github.com/InsertKoinIO/koin) : Dependency injection.
- [Retrofit2](https://github.com/square/retrofit) : REST APIs.
- [OkHttp3](https://github.com/square/okhttp) : Implementing interceptor, logging web server.
- [Glide](https://github.com/bumptech/glide) : Image loader.
- [Lottie](https://github.com/airbnb/lottie-android) : Splash animation.

</br>

## 🛠 Architecture

![clean](https://user-images.githubusercontent.com/29699217/169539214-f8b0fdb9-08a9-40ab-b577-d619f572c000.png)

</br>

## Code Convention

**Activity, Fragment Convension**
- inherit `BaseActivity` or `BaseFragment` and using `ViewBinding`
- On Activity and Fragment, work with bindings in `initViews` functions 
```kotlin
// ex
fun initViews() = with(binding) {
  // do something..
}
```
- On Activity and Fragment, work with ViewModel in `initCollects` functions
```kotlin
// ex
fun initCollects() = with(viewModel) {
  // do something..
}
```

**Test Method Convention**

given(option)_when_expect (korea language & when using spaces, use _ )

ex) `예기치 못한 에러가_발생했을때_에러페이지가 잘 보여지는가에 대한 테스트`



