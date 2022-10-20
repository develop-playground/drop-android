package com.dev.playground.data.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.dev.playground.data.R

class SharedPreferencesManager(context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.app_preferences),
            Context.MODE_PRIVATE
        )
    }

    fun removeKakaoToken() {
        preferences.edit {
            remove(KAKAO_ACCESS_TOKEN)
            remove(KAKAO_REFRESH_TOKEN)
            apply()
        }
    }

    fun setKakaoToken(token: Map<String, String>) {
        preferences.edit {
            putString(KAKAO_ACCESS_TOKEN, token["accessToken"])
            putString(KAKAO_REFRESH_TOKEN, token["refreshToken"])
            apply()
        }
    }

    fun getKakaoToken(): Map<String, String?> {
        return mapOf(
            "access_token" to preferences.getString(KAKAO_ACCESS_TOKEN, null),
            "refresh_token" to preferences.getString(KAKAO_REFRESH_TOKEN, null)
        )
    }

    companion object {
        const val KAKAO_ACCESS_TOKEN: String = "kakao_access_token"
        const val KAKAO_REFRESH_TOKEN: String = "kakao_refresh_token"
    }
}