package com.dev.playground.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import com.dev.playground.presentation.login.LoginActivity
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel.State
import com.dev.playground.presentation.util.lifecycleScope
import com.dev.playground.presentation.util.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SharedPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        loginVerification()

    }

    private fun loginVerification() {

        viewModel.getKakaoToken()

        lifecycleScope(STARTED) {
            viewModel.loginState.collect { uiState ->
                when (uiState) {
                    is State.Success -> {
                        startActivity<LoginActivity> { finish() }
                    }
                    is State.Failure -> {
                        startActivity<LoginActivity> { finish() }
                    }
                    else -> println("로딩중")
                }
            }
        }
    }
}