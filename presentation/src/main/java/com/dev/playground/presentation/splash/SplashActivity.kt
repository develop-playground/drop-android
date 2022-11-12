package com.dev.playground.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import com.dev.playground.presentation.R
import com.dev.playground.presentation.login.LoginActivity
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.splash.SplashViewModel.SplashState.Failure
import com.dev.playground.presentation.splash.SplashViewModel.SplashState.Success
import com.dev.playground.presentation.util.lifecycleScope
import com.dev.playground.presentation.util.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        viewModel.checkLoginStatus()
        initCollects()
    }

    private fun initCollects() = with(viewModel) {
        lifecycleScope(STARTED) {
            splashState.collect { uiState ->
                when (uiState) {
                    is Success -> startActivity<MainActivity> { finish() }
                    is Failure -> startActivity<LoginActivity> { finish() }
                    else -> println("로딩중")
                }
            }
        }
    }
}