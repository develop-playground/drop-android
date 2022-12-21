package com.dev.playground.presentation.ui.login

import android.content.Context
import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.type.LoginType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(private val context: Context) : LoginManager {

    private val client = UserApiClient.instance

    override fun login(
        onSuccess: (Token, LoginType) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) = with(client) {
        when (isKakaoTalkLoginAvailable(context)) {
            true -> loginWithKakaoTalk(context) { token, exception ->
                onComplete(
                    token = token,
                    exception = exception,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            }
            else -> loginWithKakaoAccount(context) { token, exception ->
                onComplete(
                    token = token,
                    exception = exception,
                    onSuccess = onSuccess,
                    onFailure = onFailure
                )
            }
        }
    }

    override fun logout(callback: (error: Throwable?) -> Unit) {
        client.logout(callback)
    }

    private fun onComplete(
        token: OAuthToken?,
        exception: Throwable?,
        onSuccess: (Token, LoginType) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        if (token != null) {
            onSuccess.invoke(
                Token(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                ),
                LoginType.KAKAO
            )
        } else if (exception != null) {
            onFailure.invoke(exception)
        }
    }

}