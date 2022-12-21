package com.dev.playground.presentation.ui.setting

import android.os.Bundle
import android.view.View
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.FragmentSettingBinding
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.FailLoadEmail
import com.dev.playground.presentation.ui.setting.SettingContract.State.Success
import com.dev.playground.presentation.util.repeatOnLifecycleState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting), ScrollableScreen {

    companion object {
        fun newInstance() = SettingFragment()

        val TAG: String = SettingFragment::class.java.simpleName
    }

    private val viewModel by viewModel<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {

    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collectLatest { state ->
                    when (state) {
                        is Success -> binding.tvEmail.text = state.email
                    }
                }
            }
            launch {
                effect.collectLatest {
                    when (it) {
                        FailLoadEmail -> {}
                    }
                }
            }
        }
    }

    override fun scrollTop() = Unit


}