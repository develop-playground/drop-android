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
            remove(KEY_KAKAO_ACCESS_TOKEN)
            remove(KEY_KAKAO_REFRESH_TOKEN)
            apply()
        }
    }

    fun setKakaoToken(token: Map<String, String>) {
        preferences.edit {
            putString(KEY_KAKAO_ACCESS_TOKEN, token[SET_ACCESS_TOKEN])
            putString(KEY_KAKAO_REFRESH_TOKEN, token[SET_REFRESH_TOKEN])
            apply()
        }
    }

    fun getKakaoToken(): Map<String, String?> {
        return mapOf(
            GET_DROP_ACCESS_TOKEN to preferences.getString(KEY_KAKAO_ACCESS_TOKEN, null),
            GET_DROP_REFRESH_TOKEN to preferences.getString(KEY_KAKAO_REFRESH_TOKEN, null)
        )
    }

    companion object {
        const val GET_DROP_ACCESS_TOKEN: String = "get_access_token"
        const val GET_DROP_REFRESH_TOKEN: String = "get_refresh_token"

        const val SET_ACCESS_TOKEN: String = "set_access_token"
        const val SET_REFRESH_TOKEN: String = "set_refresh_token"

        const val KEY_KAKAO_ACCESS_TOKEN: String = "kakao_access_token"
        const val KEY_KAKAO_REFRESH_TOKEN: String = "kakao_refresh_token"
    }
}