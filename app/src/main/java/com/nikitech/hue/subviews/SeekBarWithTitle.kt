package com.nikitech.hue.subviews

import android.content.Context
import android.os.Build
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import com.nikitech.hue.base.BaseView
import com.nikitech.hue.base.setFrame

class SeekBarWithTitle(context: Context, title: String, min: Int, max: Int) : BaseView(context) {

    private val textView = TextView(context)
    val bar = SeekBar(context)

    init {
        textView.text = title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bar.min = min
        }
        bar.max = max
        addView(textView)
        addView(bar)
    }

    override fun layoutSubviews() {

        val padding = (5 * getDensity()).toInt()
        val usableHeight = frame.height - 3 * padding

        val x = padding
        var y = padding
        val w = frame.width - 2 * padding
        var h = usableHeight / 3

        textView.setFrame(x, y, w, h)

        y += h + padding
        h = usableHeight / 3 * 2

        bar.setFrame(x, y, w, h)
    }

    fun getInnerPadding(): Int {
        return (bar.layoutParams as RelativeLayout.LayoutParams).leftMargin
    }
}