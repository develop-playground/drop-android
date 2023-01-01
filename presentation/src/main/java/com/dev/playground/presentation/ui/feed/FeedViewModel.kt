package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.usecase.memory.DeleteMemoryUseCase
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteModifyPage
import com.dev.playground.presentation.model.toPresentation
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.DeleteMemory
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.ShowRemoveDialog
import com.dev.playground.presentation.ui.feed.FeedContract.Event
import com.dev.playground.presentation.ui.feed.FeedContract.Event.*
import com.dev.playground.presentation.ui.feed.FeedContract.State
import com.dev.playground.presentation.ui.feed.FeedContract.State.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMemoryListUseCase: GetMemoryListUseCase,
    private val deleteMemoryUseCase: DeleteMemoryUseCase,
) : BaseViewModel<State, Event, UiEffect>(Loading) {

    companion object {
        private const val THRESHOLD_VISIBLE_SIZE = 3
        private const val START_INDEX = 0
    }

    private var fetchMoreJob: Job? = null
    private var pageIndex = START_INDEX

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            setState {
                Loading
            }
            pageIndex = START_INDEX
            val result = getMemoryListUseCase.invoke(pageIndex)
            result.onSuccess {
                setState {
                    Success(mapToUiModel(it))
                }
                pageIndex++
            }.onFailure {
                it.catchAuth {
                    setState {
                        Failure(it)
                    }
                }
            }
        }
    }

    private fun fetchMore(position: Int) {
        val size = (currentState as? Success)?.itemList?.size ?: return
        if (position + THRESHOLD_VISIBLE_SIZE > size || !currentState.isLoading) {
            fetchMoreJob?.cancel()
            fetchMoreJob = viewModelScope.launch {
                getMemoryListUseCase.invoke(pageIndex)
                    .onSuccess {
                        if (it.isNotEmpty()) {
                            val originalList = (currentState as? Success)?.itemList ?: return@onSuccess
                            setState {
                                val newList = mapToUiModel(it)
                                Success(originalList + newList)
                            }
                            pageIndex++
                        }
                    }
                    .onFailure {
                        it.catchAuth { }
                    }
            }
        }
    }

    private fun mapToUiModel(memoryList: List<Memory>) = memoryList.map { memory ->
        memory.toPresentation(
            onClickEdit = { bundle -> setEvent(OnClickEdit(bundle)) },
            onClickRemove = { id -> setEvent(OnClickRemove(id)) }
        )
    }

    private fun deleteMemory(id: Int) {
        viewModelScope.launch {
            deleteMemoryUseCase.invoke(id)
            setEffect {
                DeleteMemory
            }
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            Fetch -> fetch()
            is FetchMore -> fetchMore(event.position)
            is OnClickEdit -> setEffect {
                RouteModifyPage(event.bundle)
            }
            is OnClickRemove -> setEffect {
                ShowRemoveDialog(event.id)
            }
            is OnClickDeleteMemory -> deleteMemory(event.id)
        }
    }

}