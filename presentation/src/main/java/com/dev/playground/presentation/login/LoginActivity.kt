package com.dev.playground.presentation.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.dev.playground.presentation.login.LoginViewModel.State.*
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.util.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.collect
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
            println("로그인 실패 $exception")
        } else if (token != null) {
            storingTokenInLocalDataBase(token)
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
                        println(it)
                    }

                    is Failure -> {
                        println(it)
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

    private fun storingTokenInLocalDataBase(token: OAuthToken) {
        preferencesViewModel.setKakaoToken(
            mapOf(
                KAKAO_ACCESS_TOKEN to token.accessToken,
                KAKAO_REFRESH_TOKEN to token.refreshToken
            )
        )

        viewModel.login("KAKAO")
    }

}