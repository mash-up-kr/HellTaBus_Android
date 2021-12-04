package com.mashup.healthyup.bridge

import com.google.gson.JsonObject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object WebAPIController {

    private val channel = Channel<JsonObject>()
    val channelFlow: Flow<JsonObject>
        get() = channel.receiveAsFlow()

    internal fun requestAPI(
        functionName: String,
        options: JsonObject?,
        transactionId: String,
        jsInterface: JavaScriptInterface,
        preference: WebPreference
    ) {
        val requestData = JsonObject()
        val extra = JsonObject()
        var returnMsg = JsonObject()
        requestData.addProperty("functionName", functionName)
        when (functionName) {
            FunctionName.GET_SERVER_TOKEN -> {
                extra.addProperty(
                    "accessToken",
                    "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjMsImVtYWlsIjoibGVvLmxlZUBkYWFuZ24uY29tIiwiaWF0IjoxNjM4NjQ5NjA3LCJleHAiOjE2NDcyODk2MDcsImlzcyI6ImhlbGx0YWJ1cyJ9.X5EKKAeXocZIQCz_MKF3_O5del0c5cQLLBe3NOx6jZWf4FcI2d5mto9Zs3bfJATrp3kNbBcEoOK8c0rC3NrXsg"
                )
                returnMsg = makeReturnMsg(200, "Success", extra, transactionId)
            }
            FunctionName.START_ACTIVITY -> {
                if (options != null) {
                    if (options.has("target")) {
                        requestData.addProperty("target", options.get("target").asString)
                    }
                    if (options.has("loadUrl")) {
                        requestData.addProperty("loadUrl", options.get("loadUrl").asString)
                    }
                    if (options.has("exerciseList")) {
                        requestData.addProperty(
                            "exerciseList",
                            options.get("exerciseList").asString
                        )
                    }
                }
                returnMsg = makeReturnMsg(200, "Request Success", extra, transactionId)
            }
        }
        channel.trySend(requestData)
        jsInterface.onJavaScriptResponse(returnMsg)
    }

    object FunctionName {
        const val GET_SERVER_TOKEN = "getServerToken"
        const val START_ACTIVITY = "startActivity"
    }

    /** Web 에게 전달할 callback 생성 **/
    private fun makeReturnMsg(
        resultCode: Int,
        resultMsg: String,
        extra: JsonObject,
        transactionId: String
    ): JsonObject {
        return JsonObject().apply {
            addProperty("result_cd", resultCode)
            addProperty("result_msg", resultMsg)
            add("extra", extra)
            addProperty("transactionId", transactionId)
        }
    }
}
