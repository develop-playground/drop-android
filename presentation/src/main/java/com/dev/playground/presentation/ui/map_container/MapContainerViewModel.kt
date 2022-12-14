package com.dev.playground.presentation.ui.map_container

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.ui.map_container.MapContainerContract.*
import com.dev.playground.presentation.ui.map_container.MapContainerContract.State.*
import kotlinx.coroutines.launch

class MapContainerViewModel(
    private val getMemoryListUseCase: GetMemoryListUseCase
) : BaseViewModel<State, Event, UiEffect>(Idle) {

    init {
        setEvent(Event.FetchMemory)
    }

    private fun fetch() {
        viewModelScope.launch {
            val result = getMemoryListUseCase.invoke(0)
            result.onSuccess {
                setState {
                    Success(it)
                }
            }.onFailure {
                setState {
                    Failure(it)
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        (event as? Event.FetchMemory)?.run {
            fetch()
        }
    }
}