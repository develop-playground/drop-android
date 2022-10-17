package com.dev.playground.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SharedPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val token = mapOf(
            "accessToken" to "test",
            "refreshToken" to "test"
        )

        viewModel.setKakaoToken(token)

    }

}