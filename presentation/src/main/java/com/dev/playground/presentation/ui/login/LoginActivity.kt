package com.dev.playground.presentation.ui.login

import android.os.Bundle
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.ui.login.LoginContract.Effect.RouteMainPage
import com.dev.playground.presentation.ui.login.LoginContract.Effect.ShowFailRequestLoginToast
import com.dev.playground.presentation.ui.login.LoginContract.Event.OnSnsLoginComplete
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()
    private val loginManager: LoginManager = KakaoLoginManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoginManager()
        initViews()
        initCollect()
    }

    private fun initLoginManager() = loginManager.logout {
        // no-op
    }

    private fun initViews() = with(binding) {
        cvLoginButton.setOnClickListener {
            loginManager.login(
                this@LoginActivity,
                onSuccess = { token, type ->
                    viewModel.setEvent(
                        OnSnsLoginComplete(token, type)
                    )
                },
                onFailure = {
                    showToast(getString(R.string.failure_kakao_login))
                }
            )
        }
    }

    private fun initCollect() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                effect.collect {
                    when (it) {
                        RouteMainPage -> {
                            startActivity<MainActivity> {}
                            finish()
                        }
                        is ShowFailRequestLoginToast -> showToast(
                            getString(R.string.failure_request_login)
                        )
                    }
                }
            }
        }
    }

}