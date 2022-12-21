package com.dev.playground.presentation.ui.login

import android.os.Bundle
import com.dev.playground.domain.model.type.TokenType
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginEvent.SaveSNSToken
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginState.Failure
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginState.Success
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()
    private val kakaoManager: KakaoManager by lazy {
        KakaoManager(this, onSuccessLogin, onFailureLogin)
    }

    private val onSuccessLogin: (String, String) -> Unit by lazy {
        { accessToken, refreshToken ->
            viewModel.storeToken(accessToken, refreshToken, TokenType.SNS)
        }
    }

    private val onFailureLogin: (Throwable) -> Unit by lazy {
        { throwable -> showToast("카카오 로그인 실패 $throwable") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.cvLoginButton.setOnClickListener {
            kakaoManager.isKakaoLogin()
        }

        initCollect()
    }

    private fun initCollect() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                loginState.collect {
                    when (it) {
                        is Success -> storeToken(
                            it.data.accessToken,
                            it.data.refreshToken,
                            TokenType.DROP
                        )
                        is Failure -> showToast("로그인 실패 ${it.error}")
                        else -> Unit
                    }
                }
            }
            launch {
                loginEvent.collect {
                    when (it) {
                        is SaveSNSToken -> requestLogin("KAKAO")
                        else -> {
                            startActivity<MainActivity> { }
                            finish()
                        }
                    }
                }
            }

        }
    }

}