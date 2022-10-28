package com.dev.playground.presentation.login

import android.os.Bundle
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, exception ->
        if (exception != null) {
            println("로그인 성공 $token")
        } else {
            println("로그인 실패")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoSdk()

        binding.btLogin.setOnClickListener {
            isKakaoLogin()
        }

    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }

    private fun isKakaoLogin() {

        when (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(this, callback = loginCallback)
            false -> UserApiClient.instance.loginWithKakaoAccount(this, callback = loginCallback)
        }

    }

}