package com.nikitech.hue.networking

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
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

    private val queue = PriorityQueue<Int>()

    private val ip = "http://192.168.1.143/"
    private val user = "2axlCppakzSFddi19q86ixCt0WiFODG6GaQ-ST1r"
    private val groupUrl = "/groups/0/action"

    fun update(color: Int) {

        if (!queue.isEmpty()) {
            queue.add(color)
            return
        }

        sendData(color)
    }

    private fun sendData(color: Int) {
        val request = Request()
        request.httpMethod = Method.PUT
        request.url = URL( ip + "api/" + user + groupUrl)

        val body = "{\"hue\":$color, \"sat\": 254, \"bri\":150}"
        request.body(body)

        Fuel.request(request).responseJson {_, _, result ->
            result.fold(success = { json ->
                val string = json.array().toString()

                if (!queue.isEmpty()) {
                    sendData(queue.poll())
                }

                println("Success: " + string)
            }, failure = { error ->
                println("Error" + error.toString())
            })
        }
    }


}