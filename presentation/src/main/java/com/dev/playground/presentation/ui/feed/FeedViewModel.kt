package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.memory.DeleteMemoryUseCase
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteModifyPage
import com.dev.playground.presentation.model.toPresentation
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.ShowRemoveDialog
import com.dev.playground.presentation.ui.feed.FeedContract.Event
import com.dev.playground.presentation.ui.feed.FeedContract.Event.OnClickEdit
import com.dev.playground.presentation.ui.feed.FeedContract.Event.OnClickRemove
import com.dev.playground.presentation.ui.feed.FeedContract.State
import com.dev.playground.presentation.ui.feed.FeedContract.State.*
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMemoryListUseCase: GetMemoryListUseCase,
    private val deleteMemoryUseCase: DeleteMemoryUseCase,
) : BaseViewModel<State, Event, UiEffect>(Loading) {

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            // TODO paging 처리
            setState {
                Loading
            }
            val result = getMemoryListUseCase.invoke(0)
            result.onSuccess {
                setState {
                    Success(
                        it.map { memory ->
                            memory.toPresentation(
                                onClickEdit = { bundle -> setEvent(OnClickEdit(bundle)) },
                                onClickRemove = { id -> setEvent(OnClickRemove(id)) }
                            )
                        }
                    )
                }
            }.onFailure {
                it.catchAuth {
                    setState {
                        Failure(it)
                    }
                }
            }
        }
    }

    private fun deleteMemory(id: Int) {
        viewModelScope.launch {
            deleteMemoryUseCase.invoke(id)
            fetch()
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is OnClickEdit -> setEffect {
                RouteModifyPage(event.bundle)
            }
            is OnClickRemove -> setEffect {
                ShowRemoveDialog(event.id)
            }
            is Event.OnClickDeleteMemory -> deleteMemory(event.id)
        }
    }

}