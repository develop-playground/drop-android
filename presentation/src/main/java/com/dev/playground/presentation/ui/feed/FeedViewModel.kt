package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.toPresentation
import com.dev.playground.presentation.ui.feed.FeedContract.*
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.ShowEditPage
import com.dev.playground.presentation.ui.feed.FeedContract.Effect.ShowRemoveDialog
import com.dev.playground.presentation.ui.feed.FeedContract.Event.OnClickEdit
import com.dev.playground.presentation.ui.feed.FeedContract.Event.OnClickRemove
import com.dev.playground.presentation.ui.feed.FeedContract.State.*
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMemoryListUseCase: GetMemoryListUseCase,
) : BaseViewModel<State, Event, Effect>(Loading) {

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            // TODO paging 처리
            val result = getMemoryListUseCase.invoke(0)
            result.onSuccess {
                setState {
                    Success(
                        it.map { memory ->
                            memory.toPresentation(
                                onClickEdit = { id -> setEvent(OnClickEdit(id)) },
                                onClickRemove = { id -> setEvent(OnClickRemove(id)) }
                            )
                        }
                    )
                }
            }.onFailure {
                setState {
                    Failure(it)
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        setEffect {
            when (event) {
                is OnClickEdit -> ShowEditPage(event.id)
                is OnClickRemove -> ShowRemoveDialog(event.id)
            }
        }
    }

}