package com.dev.playground.presentation.feed

import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.FragmentFeedBinding

class FeedFragment: BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate), ScrollableScreen {

    companion object {
        fun newInstance() = FeedFragment()

        val TAG: String = FeedFragment::class.java.simpleName
    }

    override fun scrollTop() {
        // TODO
    }

}