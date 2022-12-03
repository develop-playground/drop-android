package com.dev.playground.presentation.ui.add

import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.ui.add.AddMemoryViewModel.AddMemoryEvent
import com.dev.playground.presentation.ui.add.AddMemoryViewModel.AddMemoryState
import com.dev.playground.presentation.ui.add.AddMemoryViewModel.AddMemoryState.Empty
import kotlinx.coroutines.flow.MutableStateFlow

class AddMemoryViewModel : BaseViewModel<AddMemoryState, AddMemoryEvent>(Empty) {

    companion object {
        private const val EMPTY_CONTENT = ""
    }

    val content: MutableStateFlow<String> = MutableStateFlow(EMPTY_CONTENT)

    fun addPhotoList() {

    }

    fun removePhoto() {

    }

    sealed interface AddMemoryState : UiState {
        object Added : UiState
        object Empty : AddMemoryState

        val isEmpty
            get() = this is Empty
    }

    sealed interface AddMemoryEvent : UiEvent {
        object DropItem : UiEvent
    }

}