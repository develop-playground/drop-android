package com.dev.playground.presentation.login

import android.os.Bundle
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityLoginBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKakaoSdk()
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }

}