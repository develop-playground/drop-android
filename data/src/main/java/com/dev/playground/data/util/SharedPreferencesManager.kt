package com.dev.playground.data.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.dev.playground.data.R
import com.dev.playground.domain.model.Auth

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val KEY_ACCESS_TOKEN: String = "access_token"
        private const val KEY_REFRESH_TOKEN: String = "refresh_token"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.app_preferences),
            Context.MODE_PRIVATE
        )
    }

    fun removeToken() {
        preferences.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
        }
    }

    fun setToken(token: Auth) {
        preferences.edit {
            putString(KEY_ACCESS_TOKEN, token.accessToken)
            putString(KEY_REFRESH_TOKEN, token.refreshToken)
        }
    }

    fun getToken(): Auth? {
        val accessToken = preferences.getString(KEY_ACCESS_TOKEN, null)
        val refreshToken = preferences.getString(KEY_REFRESH_TOKEN, null)
        return if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            null
        } else {
            Auth(accessToken, refreshToken)
        }
    }

}