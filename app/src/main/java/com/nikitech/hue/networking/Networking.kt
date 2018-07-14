package com.nikitech.hue.networking

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.nikitech.hue.model.HueColor
import java.net.URL
import java.util.*

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

    private val queue = PriorityQueue<HueColor>()

    private val ip = "http://192.168.1.143/"
    private val user = "2axlCppakzSFddi19q86ixCt0WiFODG6GaQ-ST1r"
    private val groupUrl = "/groups/0/action"

    fun update(data: HueColor) {

        if (!queue.isEmpty()) {
            queue.add(data)
            return
        }

        sendData(data)
    }

    private fun sendData(data: HueColor) {
        val request = Request()
        request.httpMethod = Method.PUT
        request.url = URL( ip + "api/" + user + groupUrl)

        val body = "{\"hue\":${data.hue}, \"sat\": ${data.saturation}, \"bri\":${data.brightness}}"
        request.body(body)

        Fuel.request(request).responseJson {_, _, result ->
            result.fold(success = { json ->
                val string = json.array().toString()

                if (!queue.isEmpty()) {
                    sendData(queue.poll())
                }

                println("Success: $string")
            }, failure = { error ->
                println("Error" + error.toString())
            })
        }
    }

}