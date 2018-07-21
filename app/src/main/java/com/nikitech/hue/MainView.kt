package com.nikitech.hue

import android.content.Context
import android.widget.ImageView
import android.widget.Switch
import com.nikitech.hue.base.BaseView
import com.nikitech.hue.base.setFrame
import com.nikitech.hue.model.LampData
import com.nikitech.hue.subviews.SeekBarWithTitle
import org.jetbrains.anko.imageResource

class MainView(context: Context) : BaseView(context) {

    val switch = Switch(context)

    val hue = SeekBarWithTitle(context, "HUE", 0, 65535)
    val hueImage = ImageView(context)

    val saturation = SeekBarWithTitle(context, "SATURATION", 0, 255)
    val brightness = SeekBarWithTitle(context, "BRIGHTNESS", 0, 255)

    init {

        addView(switch)

        switch.isChecked = true

        hueImage.imageResource = R.drawable.huebar
        hueImage.scaleType = ImageView.ScaleType.FIT_XY

        addView(hue)
        addView(hueImage)
        addView(saturation)
        addView(brightness)

        hue.bar.progress = 0
        saturation.bar.progress = 255
        brightness.bar.progress = 150

        setMainViewFrame()
    }

    override fun layoutSubviews() {

        val padding = (10 * getDensity()).toInt()

        var w = frame.width / 4
        var h = w / 3
        var x = frame.width - (w + padding)
        var y = padding

        switch.setFrame(x, y, w, h)

        x = 0
        y += h
        w = frame.width
        h = (w / 5.5).toInt()

        hue.setFrame(x, y, w, h)

        x = padding
        y += h
        h /= 3
        w = frame.width - 2 * padding

        hueImage.setFrame(x, y, w, h)

        x = hue.frame.x
        y += 2 * h
        w = hue.frame.width
        h = hue.frame.height

        saturation.setFrame(x, y, w, h)

        y += h

        brightness.setFrame(x, y, w, h)
    }

    fun getBarData(): LampData {
        return LampData(
                hue.bar.progress,
                saturation.bar.progress,
                brightness.bar.progress,
                switch.isChecked
        )
    }

    fun getDataWithCustomHue(hue: Int, lampNumber: Int): LampData {
        return LampData(
                hue,
                saturation.bar.progress,
                brightness.bar.progress,
                switch.isChecked,
                lampNumber
        )
    }
}