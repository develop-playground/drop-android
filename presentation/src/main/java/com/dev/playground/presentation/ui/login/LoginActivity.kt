package com.dev.playground.presentation.ui.login

import android.os.Bundle
import android.widget.Toast
import com.dev.playground.domain.model.type.TokenType
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginEvent.SaveSNSToken
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginState.Failure
import com.dev.playground.presentation.ui.login.LoginViewModel.LoginState.Success
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import kotlinx.coroutines.flow.collectLatest
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
        { throwable ->
            Toast.makeText(this, "카카오 로그인 실패 $throwable", Toast.LENGTH_SHORT).show()
        }
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
        repeatOnLifecycleState {
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

}