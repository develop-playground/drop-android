package com.dev.playground.presentation.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle.State.STARTED
import com.dev.playground.domain.model.type.TokenType
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.login.LoginViewModel.LoginEvent.SaveSNSToken
import com.dev.playground.presentation.login.LoginViewModel.LoginState.Failure
import com.dev.playground.presentation.login.LoginViewModel.LoginState.Success
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.util.lifecycleScope
import com.dev.playground.presentation.util.startActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit by lazy {
        { token, exception ->
            if (exception != null) {
                println("카카오 로그인 실패 $exception")
            } else if (token != null) {
                viewModel.storeToken(
                    token.accessToken,
                    token.refreshToken,
                    TokenType.SNS
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btLogin.setOnClickListener {
            isKakaoLogin()
        }

        initCollect()
    }

    private fun initCollect() = with(viewModel) {
        lifecycleScope(STARTED) {
            loginEvent.collectLatest {
                when (it) {
                    is SaveSNSToken -> requestLogin("KAKAO")
                    else -> {
                        startActivity<MainActivity> { }
                        finish()
                    }
                }
            }
        }
        lifecycleScope(STARTED) {
            loginState.collect {
                when (it) {
                    is Success -> storeToken(
                        it.data.accessToken,
                        it.data.refreshToken,
                        TokenType.DROP
                    )

                    is Failure -> {
                        // TODO
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun isKakaoLogin() =
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(this, callback = loginCallback)
            false -> UserApiClient.instance.loginWithKakaoAccount(this, callback = loginCallback)
        }

}