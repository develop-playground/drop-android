package com.dev.playground.presentation.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DimenRes
import com.dev.playground.presentation.R

class RoundRectOutlineProvider(
    @DimenRes
    private val radius: Int = R.dimen.round_rect_outline_provider_button_radius
) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        view?.apply {
            clipToOutline = true
            outline?.setRoundRect(
                0,
                0,
                width,
                height,
                resources.getDimension(radius)
            )
        }
    }
}