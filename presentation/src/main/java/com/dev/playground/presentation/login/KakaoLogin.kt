package com.dev.playground.presentation.login

import android.content.Context
import android.widget.Toast
import com.dev.playground.domain.model.type.TokenType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class KakaoLogin(
    private val context: Context,
    private val viewModel: LoginViewModel
) {

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit by lazy {
        { token, exception ->
            if (exception != null) {
                Toast.makeText(context, "카카오 로그인 실패 $exception", Toast.LENGTH_SHORT).show()
            } else if (token != null) {
                viewModel.storeToken(
                    token.accessToken,
                    token.refreshToken,
                    TokenType.SNS
                )
            }
        }
    }

    fun isKakaoLogin() =
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(context, callback = loginCallback)
            false -> UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallback)
        }

}