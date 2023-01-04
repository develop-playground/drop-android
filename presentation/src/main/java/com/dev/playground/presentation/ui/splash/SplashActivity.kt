package com.dev.playground.presentation.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivitySplashBinding
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteLoginPage
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteMainPage
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModel<SplashViewModel>()
    private val animationFlow by lazy {
        callbackFlow {
            val listener = object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    binding.lottieSplash.progress = 0f
                    trySend(Unit)
                }
                override fun onAnimationCancel(animation: Animator?) { }
                override fun onAnimationRepeat(animation: Animator?) { }
                override fun onAnimationStart(animation: Animator?) { }
            }
            binding.lottieSplash.addAnimatorListener(listener)

            awaitClose {
                binding.lottieSplash.removeAllAnimatorListeners()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCollects()
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                animationFlow.zip(effect) { _, effect ->
                    when (effect) {
                        RouteMainPage -> startActivity<MainActivity> { }
                        is RouteLoginPage -> startActivity<LoginActivity> { }
                    }
                    finish()
                }.single()
            }
        }
    }

}