package com.dev.playground.presentation.login

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class KakaoManager(
    private val context: Context,
    private val onSuccessLogin: (String, String) -> Unit,
    private val onFailureLogin: (Throwable) -> Unit
) {

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit by lazy {
        { token, exception ->
            if (exception != null) {
                onFailureLogin.invoke(exception)
            } else if (token != null) {
                onSuccessLogin.invoke(token.accessToken, token.refreshToken)
            }
        }
    }

    fun isKakaoLogin() =
        when (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            true -> UserApiClient.instance.loginWithKakaoTalk(context, callback = loginCallback)
            false -> UserApiClient.instance.loginWithKakaoAccount(context, callback = loginCallback)
        }

}