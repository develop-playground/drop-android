package com.dev.playground.presentation.ui.setting

import androidx.annotation.DrawableRes
import com.dev.playground.domain.model.type.LoginType
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class SettingContract {

    sealed interface State : UiState {
        object Idle : State
        data class Success(
            val email: String,
            val loginType: LoginType,
        ) : State

        val isSuccess
            get() = this is Success

        @get:DrawableRes
        val loginIcon: Int
            get() {
                return if (this is Success) {
                    when (loginType) {
                        LoginType.KAKAO -> R.drawable.ic_kakao_logo
                        else -> R.drawable.bg_setting_sns_icon
                    }
                } else {
                    R.drawable.bg_setting_sns_icon
                }
            }

        val userEmail
            get() = (this as? Success)?.email.orEmpty()
    }

    sealed interface Event : UiEvent {
        object OnLogout : Event
        object OnSignOut: Event
    }

    sealed interface Effect : UiEffect {
        object RouteLoginPage : Effect
        sealed interface ShowToast : Effect {
            object FailLoadUserInformation : Effect
            object FailLogout : Effect
            object FailSignOut : Effect
        }
    }

}