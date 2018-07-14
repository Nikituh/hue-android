package com.nikitech.hue

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import com.nikitech.hue.networking.Networking


class MainActivity : AppCompatActivity() {

    var contentView: MainView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = MainView(this)
        setContentView(contentView)

//        Networking.INSTANCE.getBridgeIP()
    }

    override fun onResume() {
        super.onResume()

        contentView!!.picker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {

//                val hexColor = String.format("#%06X", 0xFFFFFF and color)
//                println("Selected: $hexColor")
                val red = Color.red(color)
                val green = Color.green(color)
                val blue = Color.blue(color)

                val array = floatArrayOf(0f, 0f, 0f)
                val asdf = Color.colorToHSV(color, array)

//                val hue = getHue(red, green, blue)
                Networking.INSTANCE.update(array[0].toInt())
            }
        })
    }

    override fun onPause() {
        super.onPause()
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
