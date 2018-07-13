package com.nikitech.hue.networking

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class Networking {

    companion object {
        @JvmStatic val INSTANCE = Networking()
    }

    fun getBridgeIP(): String {
        "https://discovery.meethue.com/".httpGet().responseString { request, response, result ->
            //do something with response
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                }
                is Result.Success -> {
                    val data = result.get()
                }
            }
        }

        return ""
    }
}