package com.dev.playground.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.ActivityMainBinding
import com.dev.playground.presentation.extension.hideKeyboard
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.*
import com.dev.playground.presentation.ui.add.AddMemoryActivity
import com.dev.playground.presentation.ui.feed.FeedFragment
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainContract.Event.*
import com.dev.playground.presentation.ui.map_container.MapContainerFragment
import com.dev.playground.presentation.ui.modify.ModifyMemoryActivity
import com.dev.playground.presentation.ui.modify.ModifyMemoryActivity.Companion.KEY_MEMORY_BUNDLE
import com.dev.playground.presentation.ui.setting.SettingFragment
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import com.dev.playground.presentation.util.startActivity
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    NavigationBarView.OnItemSelectedListener {

    companion object {
        const val REFRESH_RESULT_CODE = 777
    }

    private val viewModel by viewModel<MainViewModel>()
    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == REFRESH_RESULT_CODE) {
            viewModel.setEvent(RequestRefreshMemory)
        }
    }

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
                effect.collect {
                    when (it) {
                        is RouteLoginPage -> {
                            if (it.force) {
                                showToast(R.string.please_re_log_in)
                            }
                            startActivity<LoginActivity> { }
                            finish()
                        }
                        is RouteModifyPage -> launcher.launch(
                            Intent(this@MainActivity, ModifyMemoryActivity::class.java).apply {
                                putExtra(KEY_MEMORY_BUNDLE, it.bundle)
                            }
                        )
                        RouteAddPage -> launcher.launch(
                            Intent(this@MainActivity, AddMemoryActivity::class.java)
                        )
                    }
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