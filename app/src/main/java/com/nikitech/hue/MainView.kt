package com.nikitech.hue

import android.content.Context
import android.graphics.Color
import com.madrapps.pikolo.HSLColorPicker
import com.nikitech.hue.base.BaseView
import com.nikitech.hue.base.setFrame
import org.jetbrains.anko.backgroundColor

class MainView(context: Context) : BaseView(context) {

    val picker = HSLColorPicker(context)

    init {
        addView(picker)
        setMainViewFrame()
    }

    override fun layoutSubviews() {
        picker.setFrame(0, 0, frame.width, frame.height)
    }
}