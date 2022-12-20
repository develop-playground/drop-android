package com.dev.playground.presentation.ui.feed

import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.SimpleBindingViewHolder
import com.dev.playground.presentation.databinding.ItemMemoryBinding
import com.dev.playground.presentation.model.MemoryUIModel
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.createBalloon

class FeedViewHolder(
    private val binding: ViewDataBinding,
) : SimpleBindingViewHolder<MemoryUIModel>(binding) {

    private var popup: Balloon? = null

    init {
        itemView.doOnAttach {
            setPopupMenu(it.findViewTreeLifecycleOwner())
        }
    }

    override fun onBind(item: MemoryUIModel) {
        super.onBind(item)

        onClickPopupMenu(item)
    }

    private fun setPopupMenu(lifecycleOwner: LifecycleOwner?) {
        popup = createBalloon(binding.root.context) {
            setLayout(R.layout.view_popup_memory)
            setIsVisibleArrow(false)
            setArrowSize(0)
            setMarginTopResource(R.dimen.spacing_2)
            setCornerRadiusResource(R.dimen.spacing_4)
            setBackgroundColorResource(R.color.white)
            setLifecycleOwner(lifecycleOwner)
        }
    }

    private fun onClickPopupMenu(item: MemoryUIModel) {
        (binding as? ItemMemoryBinding)?.llMemoryEdit?.setOnClickListener {
            popup?.showAsDropDown(it)
            val editButton = popup?.getContentView()?.findViewById<TextView>(R.id.tvMemoryEdit)
            val removeButton = popup?.getContentView()?.findViewById<TextView>(R.id.tvMemoryRemove)

            editButton?.setOnClickListener {
                item.onClickEdit.invoke(item.id)
                popup?.dismiss()
            }
            removeButton?.setOnClickListener {
                item.onClickRemove.invoke(item.id)
                popup?.dismiss()
            }
        }
    }

}