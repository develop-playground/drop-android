package com.dev.playground.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.UiEffect.RouteLoginPage
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.ui.splash.SplashContract.Effect.RouteMainPage
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        initCollects()
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                effect.collect {
                    when (it) {
                        RouteMainPage -> startActivity<MainActivity> { }
                        RouteLoginPage -> startActivity<LoginActivity> { }
                    }
                    finish()
                }
            }
        }
    }

}