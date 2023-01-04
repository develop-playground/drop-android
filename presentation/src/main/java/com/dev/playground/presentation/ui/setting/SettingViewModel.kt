package com.dev.playground.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.exception.NotLoggedInException
import com.dev.playground.domain.usecase.user.DeleteUserUseCase
import com.dev.playground.domain.usecase.user.GetUserEmailUseCase
import com.dev.playground.domain.usecase.user.login.GetLoginTypeUseCase
import com.dev.playground.domain.usecase.user.login.RequestLogoutUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.*
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.*
import com.dev.playground.presentation.ui.setting.SettingContract.*
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.*
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.OnOut.*
import com.dev.playground.presentation.ui.setting.SettingContract.Event.RequestLogout
import com.dev.playground.presentation.ui.setting.SettingContract.Event.RequestSignOut
import com.dev.playground.presentation.ui.setting.SettingContract.State.Idle
import com.dev.playground.presentation.ui.setting.SettingContract.State.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getLoginTypeUseCase: GetLoginTypeUseCase,
    private val requestLogoutUseCase: RequestLogoutUseCase,
) : BaseViewModel<State, Event, UiEffect>(Idle) {

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
            }.catch { e ->
                setEffect {
                    if (e is NotLoggedInException) {
                        RouteLoginPage()
                    } else {
                        ShowToast.FailLoadUserInformation
                    }
                }
            }.collect()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            requestLogoutUseCase.invoke()
                .onSuccess {
                    setEffect {
                        OnLogout
                    }
                }.onFailureWithAuth {
                    setEffect {
                        ShowToast.FailLogout
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            deleteUserUseCase.invoke()
                .onSuccess {
                    setEffect {
                        OnSignOut
                    }
                }
                .onFailureWithAuth {
                    setEffect {
                        ShowToast.FailSignOut
                    }
                }
        }
    }


    override fun handleEvent(event: Event) {
        when (event) {
            RequestLogout -> logout()
            RequestSignOut -> signOut()
        }
    }

}