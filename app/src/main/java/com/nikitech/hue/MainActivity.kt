package com.nikitech.hue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener



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

                val hexColor = String.format("#%06X", 0xFFFFFF and color)
                println("Selected: $hexColor")

            }
        })
    }

    override fun onPause() {
        super.onPause()
    }
}
