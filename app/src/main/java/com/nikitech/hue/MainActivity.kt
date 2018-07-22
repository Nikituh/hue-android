package com.nikitech.hue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import com.nikitech.hue.model.ColorCalculator
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

        contentView!!.onOff.switch.onCheckedChange { _, _ ->
            run {
                Networking.INSTANCE.update(contentView!!.getBarData())
            }
        }

        contentView!!.strobe.switch.onCheckedChange { buttonView, isChecked ->
            run {
                if (isChecked) {
                    startStrobe()
                } else {
                    normalize()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        contentView!!.hue.bar.setOnSeekBarChangeListener(null)
        contentView!!.saturation.bar.setOnSeekBarChangeListener(null)
        contentView!!.brightness.bar.setOnSeekBarChangeListener(null)
    }

    private fun startStrobe() {
        val calculator = ColorCalculator()

        val timer = object : CountDownTimer(1000 * 60, 500) {
            override fun onTick(millisUntilFinished: Long) {

                val color = calculator.getColor()
                val number = calculator.getLamp()

                val data = contentView!!.getDataWithCustomHue(color, number)
                Networking.INSTANCE.update(data)
            }
            override fun onFinish() {
                normalize()
            }
        }

        timer.start()
    }

    private fun normalize() {
        Networking.INSTANCE.update(contentView!!.getBarData())
        runOnUiThread {
            contentView!!.strobe.switch.isChecked = false
        }
    }

    /**
     * SeekBar.OnSeekBarChangeListener implementation
     */
    override fun onStopTrackingTouch(p0: SeekBar?) {
        Networking.INSTANCE.update(contentView!!.getBarData())
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        // Do nothing
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        // Do nothing
    }

}
