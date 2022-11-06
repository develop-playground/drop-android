package com.dev.playground.presentation.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.base.ScrollableScreen
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.base.SimpleBindingViewHolder
import com.dev.playground.presentation.databinding.FragmentFeedBinding
import com.dev.playground.presentation.model.PostGroupUIModel
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.showToast
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed), ScrollableScreen {

    companion object {
        fun newInstance() = FeedFragment()

        val TAG: String = FeedFragment::class.java.simpleName
    }

    private val viewModel by viewModel<FeedViewModel>()
    private val feedAdapter by lazy {
        SimpleBindingAdapter<PostGroupUIModel, SimpleBindingViewHolder<PostGroupUIModel>>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        rvFeed.itemAnimator = null
        rvFeed.adapter = feedAdapter
    }

    private fun initCollects() = with(viewModel) {
        viewLifecycleOwner.repeatOnLifecycleState {
            eventFlow.collect {
                when (it) {
                    is FeedViewModel.FeedEvent.Edit -> {
                        context?.showToast(it.id)
                    }
                }
            }
        }
        viewLifecycleOwner.repeatOnLifecycleState {
            itemList.collectLatest {
                feedAdapter.submitList(it)
                binding.tvFeedPostCount.text = it.size.toString()
            }
        }
    }

    override fun scrollTop() = binding.rvFeed.smoothScrollToPosition(0)

}