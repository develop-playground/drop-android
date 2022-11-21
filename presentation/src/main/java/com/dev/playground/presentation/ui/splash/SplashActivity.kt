package com.dev.playground.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle.State.STARTED
import com.dev.playground.presentation.R
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.ui.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.util.repeatOnLifecycleState
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

        repeatOnLifecycleState(STARTED) {
            viewModel.loginState.collect { uiState ->
                startActivity<MainActivity> { }
                finish()
//                when (uiState) {
//                    is State.Success -> {
//                        startActivity<MainActivity> { }
//                    }
//                    is State.Failure -> {
//                        startActivity<LoginActivity> { }
//                    }
//                    else -> println("로딩중")
//                }
            }
        }
    }
}