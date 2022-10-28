package com.dev.playground.presentation.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.login.LoginViewModel.State.Failure
import com.dev.playground.presentation.login.LoginViewModel.State.Success
import com.dev.playground.presentation.main.MainActivity
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.util.lifecycleScope
import com.dev.playground.presentation.util.startActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    companion object {
        const val KAKAO_ACCESS_TOKEN: String = "accessToken"
        const val KAKAO_REFRESH_TOKEN: String = "refreshToken"
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

        lifecycleScope(Lifecycle.State.STARTED) {
            viewModel.isSignIn.collect {
                when (it) {
                    is Success -> {
                        storingTokenInLocalDataBase(it.data.accessToken, it.data.refreshToken)
                        startActivity<MainActivity> { }
                    }

                    is Failure -> {
                        println("드롭 로그인 실패 & HTTP 상태 코드에 따라 토큰 재발급 로직 필요 ${it.error}")
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
                KAKAO_ACCESS_TOKEN to accessToken,
                KAKAO_REFRESH_TOKEN to refreshToken
            )
        )

        viewModel.requestLogin("KAKAO")
    }

}