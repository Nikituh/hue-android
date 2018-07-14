package com.nikitech.hue

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import com.nikitech.hue.model.HueColor
import com.nikitech.hue.networking.Networking
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private var contentView: MainView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = MainView(this)
        setContentView(contentView)
    }

    override fun onResume() {
        super.onResume()

        contentView!!.hue.bar.setOnSeekBarChangeListener(this)
        contentView!!.saturation.bar.setOnSeekBarChangeListener(this)
        contentView!!.brightness.bar.setOnSeekBarChangeListener(this)

        contentView!!.switch.onCheckedChange { _, _ ->
            run {
                Networking.INSTANCE.update(contentView!!.getColor())
            }
        }
    }

    override fun onPause() {
        super.onPause()

        contentView!!.hue.bar.setOnSeekBarChangeListener(null)
        contentView!!.saturation.bar.setOnSeekBarChangeListener(null)
        contentView!!.brightness.bar.setOnSeekBarChangeListener(null)
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        Networking.INSTANCE.update(contentView!!.getColor())
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        // Do nothing
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        // Do nothing
    }

    fun getHue(red: Int, green: Int, blue: Int): Int {

        val min = Math.min(Math.min(red, green), blue)
        val max = Math.max(Math.max(red, green), blue)

        if (min == max) {
            return 0
        }

        var hue = 0
        hue = if (max == red) {
            (green - blue) / (max - min);

        } else if (max == green) {
            2 + (blue - red) / (max - min);

        } else {
            4 + (red - green) / (max - min);
        }

        hue *= 60
        if (hue < 0) hue += 360

        return hue
    }

}
