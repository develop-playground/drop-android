package com.dev.playground.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.user.GetUserEmailUseCase
import com.dev.playground.domain.usecase.user.login.GetLoginTypeUseCase
import com.dev.playground.domain.usecase.user.login.RequestLogoutUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.ui.setting.SettingContract.*
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.RouteLoginPage
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.ShowToast
import com.dev.playground.presentation.ui.setting.SettingContract.Event.OnLogout
import com.dev.playground.presentation.ui.setting.SettingContract.State.Idle
import com.dev.playground.presentation.ui.setting.SettingContract.State.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getLoginTypeUseCase: GetLoginTypeUseCase,
    private val requestLogoutUseCase: RequestLogoutUseCase,
) : BaseViewModel<State, Event, Effect>(Idle) {

    init {
        loadUserInformation()
    }

    private fun loadUserInformation() {
        viewModelScope.launch {
            combine(
                getUserEmailUseCase.invoke(),
                getLoginTypeUseCase.invoke()
            ) { email, loginType ->
                setState {
                    Success(email = email, loginType = loginType)
                }
            }.catch {
                setEffect {
                    ShowToast.FailLoadUserInformation
                }
            }.collect()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            requestLogoutUseCase.invoke()
                .onSuccess {
                    setEffect {
                        RouteLoginPage
                    }
                }.onFailure {
                    setEffect {
                        ShowToast.FailLogout
                    }
                }
        }
    }


    override fun handleEvent(event: Event) {
        when (event) {
            OnLogout -> logout()
        }
    }

}