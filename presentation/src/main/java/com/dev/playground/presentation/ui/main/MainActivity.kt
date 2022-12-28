package com.dev.playground.presentation.ui.main

import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.MenuItem
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.ActivityMainBinding
import com.dev.playground.presentation.extension.hideKeyboard
import com.dev.playground.presentation.ui.feed.FeedFragment
import com.dev.playground.presentation.ui.login.KakaoLoginManager
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.login.LoginManager
import com.dev.playground.presentation.ui.map_container.MapContainerFragment
import com.dev.playground.presentation.ui.setting.SettingFragment
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    NavigationBarView.OnItemSelectedListener {

    private val viewModel by viewModel<MainViewModel>()
    private val loginManager: LoginManager = KakaoLoginManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initCollects()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (binding.bottomNavMain.selectedItemId == id) {
            scrollTop(id)
        }

        val tag = getTagByNavigationId(id)
        return if (tag != null) {
            showFragment(tag)
            true
        } else {
            false
        }
    }

    private fun initViews() = with(binding) {
        bottomNavMain.setOnItemSelectedListener(this@MainActivity)
        bottomNavMain.selectedItemId = R.id.menu_feed
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                routeLoginPageEffect.collectLatest {
                    loginManager.logout {
                        // no-op
                    }
                    startActivity<LoginActivity> { }
                    finish()
                }
            }
        }
    }

    private fun scrollTop(id: Int) {
        binding.root.hideKeyboard()

        val tag = getTagByNavigationId(id)
        val foundFragment = supportFragmentManager.findFragmentByTag(tag)
        if (foundFragment?.isResumed == true) {
            (foundFragment as? ScrollableScreen)?.scrollTop()
        }
    }

    private fun showFragment(tag: String) {
        binding.root.hideKeyboard()

        with(supportFragmentManager) {
            fragments.forEach {
                beginTransaction().hide(it).commitAllowingStateLoss()
            }

            val foundFragment = supportFragmentManager.findFragmentByTag(tag)

            if (foundFragment != null) {
                beginTransaction().show(foundFragment).commitAllowingStateLoss()
            } else {
                addFragment(tag)
            }
        }
    }

    private fun getTagByNavigationId(id: Int): String? = when (id) {
        R.id.menu_feed -> FeedFragment.TAG
        R.id.menu_map_container -> MapContainerFragment.TAG
        R.id.menu_setting -> SettingFragment.TAG
        else -> null
    }

    private fun addFragment(tag: String) {
        val fragment = when (tag) {
            FeedFragment.TAG -> FeedFragment.newInstance()
            MapContainerFragment.TAG -> MapContainerFragment.newInstance()
            SettingFragment.TAG -> SettingFragment.newInstance()
            else -> null
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerMain, fragment, tag)
                .commitAllowingStateLoss()
        }
    }
}