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
            _itemList.emit(
                listOf(
                    itemOf("0"),
                    itemOf("1"),
                    itemOf("2")
                )
            )
        }
    }

    private fun itemOf(id: String) = PostGroupUIModel(
        id = id,
        title = "돌고기506",
        description = "Test Description... Test Description... Test Description... " +
                "Test Description... Test Description... Test Description... " +
                "Test Description... Test Description... Test Description...",
        location = "서울시 역삼동",
        urlList = listOf(
            "https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg",
            "https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg",
            "https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg"
        ),
    ) {
        event(FeedEvent.Edit(it))
    }

    sealed interface FeedEvent : Event {
        data class Edit(val id: String) : FeedEvent
    }

}