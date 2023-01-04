package com.dev.playground.presentation.ui.setting

import android.os.Bundle
import android.view.View
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.FragmentSettingBinding
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteLoginPage
import com.dev.playground.presentation.model.base.UiEvent.NavigationEvent.RequestRouteLogin
import com.dev.playground.presentation.ui.dialog.DropDialog
import com.dev.playground.presentation.ui.dialog.show
import com.dev.playground.presentation.ui.main.MainViewModel
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.OnOut
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.ShowToast
import com.dev.playground.presentation.ui.setting.SettingContract.Event.RequestLogout
import com.dev.playground.presentation.ui.setting.SettingContract.Event.RequestSignOut
import com.dev.playground.presentation.util.VERSION_NAME
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting), ScrollableScreen {

    companion object {
        fun newInstance() = SettingFragment()
        val TAG: String = SettingFragment::class.java.simpleName
    }

    private val viewModel by viewModel<SettingViewModel>()
    private val sharedViewModel by sharedViewModel<MainViewModel>()
    private val versionNumber: String by inject(named(VERSION_NAME))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        vm = viewModel
        tvServiceTerms.setOnClickListener {
            context?.showToast("TODO : 서비스 이용약관 WebView 표시!")
        }
        tvPrivateTerms.setOnClickListener {
            context?.showToast("TODO : 개인정보 취급방침 WebView 표시!")
        }
        tvCurrentVersion.text = versionNumber
        tvLogout.setOnClickListener {
            viewModel.setEvent(RequestLogout)
        }
        tvSignOut.setOnClickListener {
            context?.let {
                DropDialog(it).show {
                    contentText = getString(R.string.setting_sign_out_content)
                    leftText = getString(R.string.setting_sign_out_cancel)
                    rightText = getString(R.string.setting_sign_out_ok)
                    onRightClick = {
                        viewModel.setEvent(RequestSignOut)
                    }
                }
            }
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                effect.collect {
                    when (it) {
                        is OnOut -> {
                            context?.showToast(it.message)
                            sharedViewModel.setEvent(RequestRouteLogin(false))
                        }
                        is RouteLoginPage -> sharedViewModel.setEvent(RequestRouteLogin())
                        is ShowToast -> context?.showToast(it.message)
                    }
                }
            }
        }
    }

    override fun scrollTop() = Unit

}