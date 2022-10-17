package com.dev.playground.presentation.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.dev.playground.presentation.R

class SharedPreferencesManager(context: Context) {

    private val mPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.app_preferences),
            Context.MODE_PRIVATE
        )
    }

    private val preferencesEditor: SharedPreferences.Editor by lazy {
        mPreferences.edit()
    }

    fun setKakaoToken(accessToken: String, refreshToken: String) {
        preferencesEditor.apply {
            putString(KAKAO_ACCESS_TOKEN, accessToken)
            putString(KAKAO_REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun getKakaoToken(): Map<String, String?> {
        return mapOf(
            "access_token" to mPreferences.getString(KAKAO_ACCESS_TOKEN, "kakao_access_token"),
            "refresh_token" to mPreferences.getString(KAKAO_REFRESH_TOKEN, "kakao_refresh_token")
        )
    }

    companion object {
        const val KAKAO_ACCESS_TOKEN: String = "kakao_access_token"
        const val KAKAO_REFRESH_TOKEN: String = "kakao_refresh_token"
    }

}