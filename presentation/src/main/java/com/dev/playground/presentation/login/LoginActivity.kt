package com.dev.playground.presentation.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle.State.STARTED
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.login.LoginViewModel.State.Failure
import com.dev.playground.presentation.login.LoginViewModel.State.Success
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel.State
import com.dev.playground.presentation.util.errorStatusCode
import com.dev.playground.presentation.util.lifecycleScope
import com.dev.playground.presentation.util.startActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    companion object {
        const val GET_DROP_ACCESS_TOKEN: String = "get_access_token"
        const val GET_DROP_REFRESH_TOKEN: String = "get_refresh_token"
        const val SET_ACCESS_TOKEN: String = "set_access_token"
        const val SET_REFRESH_TOKEN: String = "set_refresh_token"
    }

    private val viewModel: LoginViewModel by viewModel()
    private val preferencesViewModel: SharedPreferencesViewModel by viewModel()

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, exception ->
        if (exception != null) {
            println("카카오 로그인 실패 $exception")
        } else if (token != null) {
            storingTokenInLocalDataBase(token.accessToken, token.refreshToken)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoSdk()

        binding.btLogin.setOnClickListener {
            isKakaoLogin()
        }

        isTokenInvalidate()
    }

    private fun isTokenInvalidate() {
        lifecycleScope(STARTED) {
            viewModel.isSignIn.collect {
                when (it) {
                    is Success -> {
                        storingTokenInLocalDataBase(it.data.accessToken, it.data.refreshToken)
                        startActivity<MainActivity> { }
                    }

                    is Failure -> {
                        when (errorStatusCode(it.error)) {
                            "403" -> {
                                reIssueRefreshToken()
                            }
                            else -> println("추가 에러 로직 필요")
                        }
                    }
                }
            }
        }
    }

    private fun reIssueRefreshToken() {
        preferencesViewModel.getKakaoToken()

        lifecycleScope(STARTED) {
            preferencesViewModel.loginState.collect { uiState ->
                when (uiState) {
                    is State.Success -> {
                        uiState.data[GET_DROP_REFRESH_TOKEN]?.let { viewModel.reIssueToken(it) }
                    }

                    is State.Failure -> {
                        println("로컬에 저장된 토큰 가져오기 실패")
                    }
                }
            }
        }
    }

    private fun initKakaoSdk() = KakaoSdk.init(this, getString(R.string.kakao_native_app_key))


    private fun isKakaoLogin() =
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(this, callback = loginCallback)
            false -> UserApiClient.instance.loginWithKakaoAccount(this, callback = loginCallback)
        }

    private fun storingTokenInLocalDataBase(accessToken: String, refreshToken: String) {
        preferencesViewModel.setKakaoToken(
            mapOf(
                SET_ACCESS_TOKEN to accessToken,
                SET_REFRESH_TOKEN to refreshToken
            )
        )

        viewModel.requestLogin("KAKAO")
    }

}