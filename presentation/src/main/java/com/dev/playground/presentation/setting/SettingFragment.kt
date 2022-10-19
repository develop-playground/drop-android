package com.dev.playground.presentation.setting

import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    companion object {
        fun newInstance() = SettingFragment()

        val TAG: String = SettingFragment::class.java.simpleName
    }

}