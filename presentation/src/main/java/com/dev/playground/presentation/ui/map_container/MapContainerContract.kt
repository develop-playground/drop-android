package com.dev.playground.presentation.ui.map_container

import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class MapContainerContract {

    sealed interface State : UiState {
        object Idle: State
        data class Success(val itemList: List<Memory>) : State
        data class Failure(val exception: Throwable) : State
    }

    sealed interface Event : UiEvent {
        object FetchMemory: Event
    }

}