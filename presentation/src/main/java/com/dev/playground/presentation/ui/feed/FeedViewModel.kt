package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.PostUIModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FeedViewModel : BaseViewModel<FeedViewModel.FeedEvent>() {

    private val _itemList: MutableStateFlow<List<PostUIModel>> = MutableStateFlow(emptyList())
    val itemList: StateFlow<List<PostUIModel>> = _itemList.asStateFlow()

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
        PostUIModel.PostTitleUIModel("돌고기506", "서울시 역삼동") {
            event(FeedEvent.Edit(it))
        },
        PostUIModel.PostImageCarouselUIModel(
            listOf()
        ),
        PostUIModel.PostDescriptionUIModel(
            "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description..."
        ),
        PostUIModel.PostTitleUIModel("동대문디자인플라자", "서울시 중구") {
            event(FeedEvent.Edit(it))
        },
        PostUIModel.PostImageCarouselUIModel(
            listOf()
        ),
        PostUIModel.PostDescriptionUIModel(
            "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description... " +
                    "Test Description... Test Description... Test Description..."
        )
    )

    sealed interface FeedEvent : Event {
        data class Edit(val title: String) : FeedEvent
    }

}