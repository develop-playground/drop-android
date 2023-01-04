package com.dev.playground.presentation.ui.splash

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(2450L)
        }
        initCollects()
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                effect.collect {
                    when (it) {
                        RouteMainPage -> startActivity<MainActivity> { }
                        is RouteLoginPage -> startActivity<LoginActivity> { }
                    }
                    finish()
                }
            }
        }
    }

}