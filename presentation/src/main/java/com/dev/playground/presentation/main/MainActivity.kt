package com.dev.playground.presentation.main

import android.os.Bundle
import android.view.MenuItem
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.ActivityMainBinding
import com.dev.playground.presentation.extension.hideKeyboard
import com.dev.playground.presentation.feed.FeedFragment
import com.dev.playground.presentation.map_container.MapContainerFragment
import com.dev.playground.presentation.setting.SettingFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    NavigationBarView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
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
    }

    private fun scrollTop(id: Int) {
        binding.root.hideKeyboard()

        val tag = getTagByNavigationId(id)
        val foundFragment = supportFragmentManager.findFragmentByTag(tag)
        (foundFragment as? ScrollableScreen)?.scrollTop()
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