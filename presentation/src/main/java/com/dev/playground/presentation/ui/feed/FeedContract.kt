package com.dev.playground.presentation.ui.feed

import com.dev.playground.presentation.model.MemoryBundle
import com.dev.playground.presentation.model.MemoryUIModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class FeedContract {

    companion object {
        private const val EMPTY_SIZE = "0"
    }

    sealed interface State : UiState {
        data class Success(val itemList: List<MemoryUIModel>) : State
        object Loading : State
        data class Failure(val exception: Throwable) : State

        val isSuccess
            get() = this is Success && this.itemList.isNotEmpty()

        val hasError
            get() = this is Failure

        val isEmpty
            get() = this is Success && this.itemList.isEmpty()

        val isLoading
            get() = this is Loading

        val itemSize: String
            get() = if (this is Success && this.itemList.isNotEmpty()) {
                itemList.size.toString()
            } else {
                EMPTY_SIZE
            }
    }

    sealed interface Event : UiEvent {
        object Fetch: Event
        data class FetchMore(val position: Int): Event
        data class OnClickEdit(val bundle: MemoryBundle) : Event
        data class OnClickRemove(val id: Int) : Event
        data class OnClickDeleteMemory(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class RouteEditPage(val bundle: MemoryBundle) : Effect
        data class ShowRemoveDialog(val id: Int) : Effect
    }

}
