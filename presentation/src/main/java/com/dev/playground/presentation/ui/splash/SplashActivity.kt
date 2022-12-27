package com.dev.playground.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.playground.presentation.R
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.ui.splash.SplashViewModel.SplashState.Failure
import com.dev.playground.presentation.ui.splash.SplashViewModel.SplashState.Success
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        viewModel.checkLoginStatus()

        CoroutineScope(Dispatchers.IO).launch {
            delay(2450L)
            initCollects()
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                splashState.collect { uiState ->
                    when (uiState) {
                        is Success -> startActivity<MainActivity> { }
                        is Failure -> startActivity<LoginActivity> { }
                        else -> Unit
                    }
                    finish()
                }
            }
        }
    }
}