package com.dev.playground.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dev.playground.presentation.login.LoginActivity
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.util.UiState
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SharedPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        loginVerification()
    }

    private fun loginVerification() {
        lifecycleScope.launch {
            viewModel.getKakaoToken()

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            startActivity<MainActivity> { }
                        }
                        is UiState.Failure -> {
                            startActivity<LoginActivity> { }
                        }
                        else -> println("로딩중")
                    }
                }
            }
        }
    }

}