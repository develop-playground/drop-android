package com.dev.playground.presentation.ui.modify

import android.os.Bundle
import androidx.viewpager2.widget.MarginPageTransformer
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.base.SimpleBindingViewHolder
import com.dev.playground.presentation.databinding.ActivityModifyMemoryBinding
import com.dev.playground.presentation.model.MemoryBundle
import com.dev.playground.presentation.model.ModifyPhotoUIModel
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.Effect.ShowFailureModifyToast
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.Effect.SuccessModified
import com.dev.playground.presentation.ui.modify.ModifyMemoryContract.Event.RequestModify
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ModifyMemoryActivity : BaseActivity<ActivityModifyMemoryBinding>(R.layout.activity_modify_memory) {

    companion object {
        const val KEY_MEMORY_BUNDLE = "key_memory_bundle"
    }

    private lateinit var viewModel: ModifyMemoryViewModel
    private val photoAdapter = SimpleBindingAdapter<ModifyPhotoUIModel, SimpleBindingViewHolder<ModifyPhotoUIModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = intent.getParcelableExtra<MemoryBundle>(KEY_MEMORY_BUNDLE) ?: finish()
        viewModel = getViewModel {
            parametersOf(model)
        }

        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        vm = viewModel
        tvClose.setOnClickListener {
            finish()
        }
        btModifyMemory.setOnClickListener {
            viewModel.setEvent(RequestModify)
        }
        vpPhotoList.apply {
            adapter = photoAdapter
            offscreenPageLimit = 1
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.spacing_4)))
        }
        TabLayoutMediator(tlPhotoPager, vpPhotoList) { _, _ -> }.attach()
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collect {
                    photoAdapter.submitList(
                        it.bundle.urlList.map { url -> ModifyPhotoUIModel(url) }
                    )
                }
            }
            launch {
                effect.collect {
                    when (it) {
                        SuccessModified -> {
                            // TODO result 처리해서 refresh 유도
                            finish()
                        }
                        ShowFailureModifyToast -> showToast(getString(R.string.modify_failure_please_retry))
                    }
                }
            }
        }
    }

}