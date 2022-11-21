package com.dev.playground.presentation.ui.feed

import androidx.lifecycle.viewModelScope
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.ImageCarouselItemUIModel
import com.dev.playground.presentation.model.MemoryUIModel
import com.dev.playground.presentation.ui.feed.FeedViewModel.FeedEvent
import com.dev.playground.presentation.ui.feed.FeedViewModel.FeedState
import kotlinx.coroutines.launch

// TODO 로컬 DB or 네트워크에서 실제 데이터 받아와야 함.
class FeedViewModel : BaseViewModel<FeedState, FeedEvent>(FeedState.Loading) {

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            updateState {
                FeedState.Success(
                    itemList = listOf(
                        itemOf("0"),
                        itemOf("1"),
                        itemOf("2")
                    )
                )
            }
        }
    }

    private fun itemOf(id: String) = MemoryUIModel(
        id = id,
        title = "돌고기506",
        description = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세 무궁화 삼천리 화려강산 대한사람 대한으로 길이 보전하세",
        location = "서울시 역삼동",
        imageList = listOf(
            ImageCarouselItemUIModel("https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg"),
            ImageCarouselItemUIModel("https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg"),
            ImageCarouselItemUIModel("https://m.convenii.com/web/upload/NNEditor/20210806/mobile/a0b819501ea9caeccd00d197066ba2d0_1628245087.jpg"),
        ),
    ) {
        event(FeedEvent.Edit(it))
    }

    sealed interface FeedEvent : UiEvent {
        data class Edit(val id: String) : FeedEvent
    }

    sealed interface FeedState : UiState {
        data class Success(val itemList: List<MemoryUIModel>) : FeedState
        object Loading : FeedState

        val isSuccess
            get() = this is Success && this.itemList.isNotEmpty()

        val isEmpty
            get() = this is Success && this.itemList.isEmpty()
    }

}