package com.nikitech.hue.subviews

import android.content.Context
import android.view.Gravity
import android.widget.Switch
import android.widget.TextView
import com.nikitech.hue.base.BaseView
import com.nikitech.hue.base.setFrame

class SwitchWithTitle(context: Context, title: String) : BaseView(context) {

    private val titleView = TextView(context)
    val switch = Switch(context)

    init {

        titleView.text = title
        titleView.gravity = Gravity.CENTER_VERTICAL

        addView(titleView)
        addView(switch)
    }

    override fun layoutSubviews() {
        super.layoutSubviews()

        val padding = (5 * getDensity()).toInt()

        var x = padding
        val y = 0
        var w = frame.width / 3 * 2 - padding
        val h = frame.height

        titleView.setFrame(x, y, w, h)

        x += w
        w = frame.width / 3

        switch.setFrame(x, y, w, h)
    }
}