package com.nikitech.hue.model

class ColorCalculator {

    private val lampCount = 3
    private var lastLamp  = 0

    private val yellow = 10000
    private val green = 20000
    private val blue = 40000
    private val red = 60000
    private val colors = arrayListOf(yellow, green, blue, red)
    private var lastColor = -1

    fun getLamp(): Int {

        if (lastLamp == lampCount) {
            lastLamp = 1
        } else {
            lastLamp += 1
        }

        return lastLamp
    }

    fun getColor(): Int {

        if (lastColor == colors.size - 1) {
            lastColor = 0
        } else {
            lastColor += 1
        }

        return colors[lastColor]
    }
}