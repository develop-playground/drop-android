package com.dev.playground.presentation.ui.modify

import com.dev.playground.presentation.model.MemoryBundle
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class ModifyMemoryContract {

    data class State(
        val bundle: MemoryBundle,
        val isLoading: Boolean = false,
    ) : UiState {

        val isOnlyOne
            get() = bundle.urlList.size == 1

    }

    sealed interface Event : UiEvent {
        object RequestModify : Event
    }

    sealed interface Effect : UiEffect {
        object ShowFailureModifyToast : Effect
        object SuccessModified : Effect
    }

}