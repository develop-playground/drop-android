package com.dev.playground.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.playground.presentation.R
import com.dev.playground.presentation.ui.splash.SplashViewModel.SplashState.Failure
import com.dev.playground.presentation.ui.splash.SplashViewModel.SplashState.Success
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.repeatOnLifecycleState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dev.playground.presentation.util.startActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        viewModel.checkLoginStatus()
        initCollects()
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            splashState.collect { uiState ->
                when (uiState) {
                    is Success -> startActivity<MainActivity> {  }
                    is Failure -> startActivity<LoginActivity> {  }
                    else -> println("로딩중")
                }
                finish()
            }
        }
    }
}