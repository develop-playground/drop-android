package com.dev.playground.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.user.GetUserUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.ui.setting.SettingContract.*
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.FailLoadEmail
import com.dev.playground.presentation.ui.setting.SettingContract.State.Idle
import com.dev.playground.presentation.ui.setting.SettingContract.State.Success
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel<State, Event, Effect>(Idle) {

    init {
        loadEmail()
    }

    private fun loadEmail() {
        viewModelScope.launch {
            getUserUseCase.invoke().onSuccess {
                setState {
                    Success(it.email)
                }
            }.onFailure {
                setEffect {
                    FailLoadEmail
                }
            }
        }
    }


    override fun handleEvent(event: Event) {
        when (event) {

        }
    }

}