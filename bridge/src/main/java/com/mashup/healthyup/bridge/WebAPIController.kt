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
                    "Bearer ${preference.get("token", "")}"
                )
                returnMsg = makeReturnMsg(200, "Success", extra, transactionId)
            }
            FunctionName.START_ACTIVITY -> {
                if (options != null) {
                    if (options.has("target")) {
                        requestData.addProperty("target", options.get("target").asString)
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
