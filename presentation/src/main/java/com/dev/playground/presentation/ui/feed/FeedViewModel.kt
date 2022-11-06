package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.PostGroupUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel : BaseViewModel<FeedViewModel.FeedEvent>() {

    private val _itemList: MutableStateFlow<List<PostGroupUIModel>> = MutableStateFlow(emptyList())
    val itemList: StateFlow<List<PostGroupUIModel>> = _itemList.asStateFlow()

    init {
        fetch()
    }

    // TODO get real data
    private fun fetch() {
        viewModelScope.launch {
            _itemList.emit(itemListOf())
        }
    }

    private fun itemListOf() = listOf(
        PostGroupUIModel(
            id = "0",
            title = "돌고기506",
            description = "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description...",
            location = "서울시 역삼동",
            urlList = listOf(),
        ) {
            event(FeedEvent.Edit(it))
        }
    )

    sealed interface FeedEvent : Event {
        data class Edit(val title: String) : FeedEvent
    }

}