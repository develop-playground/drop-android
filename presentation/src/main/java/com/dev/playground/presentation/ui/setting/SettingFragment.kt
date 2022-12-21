package com.dev.playground.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.charlezz.pickle.util.ext.showToast
import com.dev.playground.domain.model.type.LoginType
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.FragmentSettingBinding
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.RouteLoginPage
import com.dev.playground.presentation.ui.setting.SettingContract.Effect.ShowToast
import com.dev.playground.presentation.ui.setting.SettingContract.State.Success
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
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
        vm = viewModel
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                effect.collect {
                    when (it) {
                        RouteLoginPage -> {
                            showToast(getString(R.string.logged_out))
                            context?.startActivity<LoginActivity> {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            }
                        }
                        ShowToast.FailLoadUserInformation -> showToast("사용자 정보를 불러오는데 실패했습니다.")
                        ShowToast.FailLogout -> showToast("로그아웃에 실패했습니다. 다시 시도해주세요")
                    }
                }
            }
        }
    }

    override fun scrollTop() = Unit


}