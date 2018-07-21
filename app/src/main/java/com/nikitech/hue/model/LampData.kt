package com.nikitech.hue.model

class LampData(
        val hue: Int,
        val saturation: Int,
        val brightness: Int,
        val isChecked: Boolean,
        val lampNumber: Int? = null) {

    fun isGroupCommand(): Boolean {
        return lampNumber == null
    }

}