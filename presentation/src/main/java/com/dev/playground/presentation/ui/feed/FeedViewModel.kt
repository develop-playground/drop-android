package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.MemoryUIModel
import com.dev.playground.presentation.model.toPresentation
import com.dev.playground.presentation.ui.feed.FeedViewModel.FeedEvent
import com.dev.playground.presentation.ui.feed.FeedViewModel.FeedState
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMemoryListUseCase: GetMemoryListUseCase,
) : BaseViewModel<FeedState, FeedEvent>(FeedState.Loading) {

    companion object {
        private const val EMPTY_SIZE = "0"
    }

    init {
        fetch()
    }

    fun fetch() {
        updateState { FeedState.Loading }

        viewModelScope.launch {
            // TODO paging 처리
            val result = getMemoryListUseCase.invoke(0)
            result.onSuccess {
                updateState {
                    FeedState.Success(it.map {
                        it.toPresentation(
                            onClickEdit = { id -> event(FeedEvent.Edit(id)) },
                            onClickRemove = { id -> event(FeedEvent.Remove(id)) }
                        )
                    })
                }
            }.onFailure {
                updateState { FeedState.Failure(it) }
            }
        }
    }

    sealed interface FeedEvent : UiEvent {
        data class Edit(val id: Int) : FeedEvent
        data class Remove(val id: Int) : FeedEvent
    }

    sealed interface FeedState : UiState {
        data class Success(val itemList: List<MemoryUIModel>) : FeedState
        object Loading : FeedState
        data class Failure(val exception: Throwable) : FeedState

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

}