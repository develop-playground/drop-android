package com.dev.playground.presentation.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.databinding.FragmentFeedBinding
import com.dev.playground.presentation.util.repeatOnLifecycleState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(FragmentFeedBinding::inflate), ScrollableScreen {

    companion object {
        fun newInstance() = FeedFragment()

        val TAG: String = FeedFragment::class.java.simpleName
    }

    private val viewModel by viewModel<FeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        rvFeed.itemAnimator = null
    }

    private fun initCollects() = with(viewModel) {
        viewLifecycleOwner.repeatOnLifecycleState {
            eventFlow.collect {
                when (it) {
                    is FeedViewModel.FeedEvent.Edit -> {
                        context?.let { c -> Toast.makeText(c, it.title, Toast.LENGTH_SHORT) }
                    }
                }
            }
        }
    }

    override fun scrollTop() = binding.rvFeed.smoothScrollToPosition(0)

}