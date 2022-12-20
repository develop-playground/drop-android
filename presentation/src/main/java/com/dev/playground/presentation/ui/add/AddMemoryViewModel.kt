package com.dev.playground.presentation.ui.add

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.model.MemoryInput
import com.dev.playground.domain.usecase.location.GetAddressUseCase
import com.dev.playground.domain.usecase.memory.PostMemoryUseCase
import com.dev.playground.domain.usecase.photo.DeletePhotoUseCase
import com.dev.playground.domain.usecase.photo.UploadPhotoUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.PhotoUIModel
import com.dev.playground.presentation.ui.add.AddMemoryContract.*
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.Empty
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.SelectedPhoto
import com.dev.playground.presentation.ui.add.AddMemoryContract.Effect.Dropped
import com.dev.playground.presentation.ui.add.AddMemoryContract.Effect.ShowErrorToast.*
import com.dev.playground.presentation.ui.add.AddMemoryContract.Event.OnClickDrop
import com.dev.playground.presentation.ui.add.AddMemoryContract.Event.OnClickRemovePhoto
import com.dev.playground.presentation.util.currentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class AddMemoryViewModel(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val postMemoryUseCase: PostMemoryUseCase,
    private val getAddressUseCase: GetAddressUseCase,
) : BaseViewModel<State, Event, Effect>(State(Empty)) {

    companion object {
        private const val EMPTY_CONTENT = ""
    }

    val content: MutableStateFlow<String> = MutableStateFlow(EMPTY_CONTENT)

    override fun handleEvent(event: Event) {
        when (event) {
            is OnClickRemovePhoto -> removePhoto(event.index)
            OnClickDrop -> drop()
        }
    }

    fun selectPhotoList(
        result: List<File>,
        latLong: FloatArray?,
    ) {
        if (result.isEmpty()) {
            setEffect { NotSelectPhoto }
            return
        }

        val latitude = latLong?.getOrNull(0)?.toDouble()
        val longitude = latLong?.getOrNull(1)?.toDouble()

        viewModelScope.launch {
            if (latitude != null && longitude != null) {
                val location = Memory.Location(
                    longitude = longitude,
                    latitude = latitude
                )
                getAddressUseCase.invoke(location)
                    .onSuccess {
                        setState {
                            copy(
                                addMemoryState = SelectedPhoto(
                                    fileList = result,
                                    information = SelectedPhoto.Information(
                                        location = location,
                                        address = it,
                                        createdDate = currentDate()
                                    )
                                )
                            )
                        }
                    }
                    .onFailure {
                        setEffect { EmptyLocation }
                    }
            } else {
                setEffect { EmptyLocation }
            }
        }
    }

    fun mapToUIModel(state: SelectedPhoto): List<PhotoUIModel> = state.fileList.mapIndexed { index, file ->
        PhotoUIModel(index = index, file = file) {
            setEvent(OnClickRemovePhoto(it))
        }
    }

    fun drop() {
        viewModelScope.launch {
            when (val state = currentState.addMemoryState) {
                is SelectedPhoto -> {
                    setState { copy(isLoading = true) }

                    uploadPhotoUseCase.invoke(state.fileList).collectLatest { result ->
                        result
                            .onSuccess { urlList ->
                                postMemory(urlList = urlList, oldState = state)
                            }
                            .onFailure {
                                setEffect { FailUpload }
                                setState { copy(isLoading = false) }
                            }
                    }
                }
                else -> setEffect { NotSelectPhoto }
            }
        }
    }

    private suspend fun postMemory(
        urlList: List<String>,
        oldState: SelectedPhoto,
    ) {
        postMemoryUseCase.invoke(
            MemoryInput(
                imageUrls = urlList,
                content = content.value,
                location = oldState.information.location,
                address = oldState.information.address
            )
        ).onSuccess {
            setEffect { Dropped }
        }.onFailure {
            deletePhoto(oldState.fileList)
            setEffect { FailUpload }
        }
        setState { copy(isLoading = false) }
    }

    private suspend fun deletePhoto(params: List<File>) {
        deletePhotoUseCase.invoke(params)
    }

    private fun removePhoto(index: Int) {
        val oldState = (currentState.addMemoryState as? SelectedPhoto) ?: return

        val newFileList = oldState.fileList.toMutableList()
        newFileList.removeAt(index)

        setState {
            if (newFileList.isNotEmpty()) {
                copy(
                    addMemoryState = SelectedPhoto(
                        fileList = newFileList,
                        information = oldState.information,
                    )
                )
            } else {
                copy(addMemoryState = Empty)
            }
        }
    }

}