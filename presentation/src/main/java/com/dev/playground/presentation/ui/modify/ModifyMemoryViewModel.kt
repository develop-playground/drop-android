package com.dev.playground.presentation.ui.modify

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.MemoryModifyInput
import com.dev.playground.domain.usecase.memory.ModifyMemoryUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.MemoryBundle
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.*
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.Effect.ShowFailureModifyToast
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.Effect.SuccessModified
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ModifyMemoryViewModel(
    private val bundle: MemoryBundle,
    private val modifyMemoryUseCase: ModifyMemoryUseCase,
) : BaseViewModel<State, Event, Effect>(State(bundle)) {

    val content: MutableStateFlow<String> = MutableStateFlow(bundle.content)

    private fun modify() {
        viewModelScope.launch {
            setState {
                copy(isLoading = true)
            }
            modifyMemoryUseCase
                .invoke(MemoryModifyInput(id = bundle.id, content = content.value))
                .onSuccess {
                    setEffect {
                        SuccessModified
                    }
                }
                .onFailure {
                    setEffect {
                        ShowFailureModifyToast
                    }
                }

            setState {
                copy(isLoading = false)
            }
        }
    }

    override fun handleEvent(event: Event) {
        (event as? Event.RequestModify)?.run {
            modify()
        }
    }

}