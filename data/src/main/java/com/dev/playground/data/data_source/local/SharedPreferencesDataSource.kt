package com.dev.playground.data.data_source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.dev.playground.data.R
import com.dev.playground.domain.model.Token

class SharedPreferencesDataSource(context: Context) {
    companion object {
        private const val KEY_ACCESS_TOKEN: String = "access_token"
        private const val KEY_REFRESH_TOKEN: String = "refresh_token"
        private const val KEY_LOGIN_TYPE: String = "login_type"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.app_preferences),
            Context.MODE_PRIVATE
        )
    }

    fun getToken(): Token? {
        val accessToken = preferences.getString(KEY_ACCESS_TOKEN, null)
        val refreshToken = preferences.getString(KEY_REFRESH_TOKEN, null)
        return if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            null
        } else {
            Token(accessToken, refreshToken)
        }
    }

    fun setToken(token: Token) {
        preferences.edit {
            putString(KEY_ACCESS_TOKEN, token.accessToken)
            putString(KEY_REFRESH_TOKEN, token.refreshToken)
        }
    }

    fun removeAuth() {
        preferences.edit {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            remove(KEY_LOGIN_TYPE)
        }
    }

    fun getLoginType(): String? {
        return preferences.getString(KEY_LOGIN_TYPE, null)
    }

    fun setLoginType(type: String) {
        preferences.edit {
            putString(KEY_LOGIN_TYPE, type)
        }
    }

}