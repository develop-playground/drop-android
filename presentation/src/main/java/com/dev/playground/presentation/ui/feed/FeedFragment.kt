package com.dev.playground.presentation.ui.feed

import android.os.Bundle
import android.view.View
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.databinding.FragmentFeedBinding
import com.dev.playground.presentation.ui.feed.FeedViewModel.FeedEvent
import com.dev.playground.presentation.util.repeatOnLifecycleState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed), ScrollableScreen {

    companion object {
        fun newInstance() = FeedFragment()

        val TAG: String = FeedFragment::class.java.simpleName
    }

    private val viewModel by viewModel<FeedViewModel>()
    private val feedAdapter by lazy {
        SimpleBindingAdapter(FeedViewHolder::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding.rvFeed) {
        itemAnimator = null
        adapter = feedAdapter
        addItemDecoration(FeedItemDecoration())
    }

    private fun initCollects() = with(viewModel) {
        viewLifecycleOwner.repeatOnLifecycleState {
            eventFlow.collect {
                when (it) {
                    is FeedEvent.Edit -> {

                    }
                    is FeedEvent.Remove -> {

                    }
                }
            }
        }
        viewLifecycleOwner.repeatOnLifecycleState {
            itemList.collectLatest {
                feedAdapter.submitList(it)
                binding.tvFeedMemoryCount.text = it.size.toString()
            }
        }
    }

    override fun scrollTop() = binding.rvFeed.smoothScrollToPosition(0)

}