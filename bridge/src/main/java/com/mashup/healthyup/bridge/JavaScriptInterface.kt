package com.mashup.healthyup.bridge

import android.app.LauncherActivity
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/** WebView 에 올라온 URL 의 JavaScript 와 통신하는 부분 **/
class JavaScriptInterface(private val webView: HealthyUpWebView) : JavaScriptInterfaceCallback {

    private val TAG = javaClass.simpleName
    private val handler = Handler()
    var targetActivityName: MutableLiveData<String> = MutableLiveData()

    override fun onJavaScriptResponse(eventData: JsonObject) {
        val extra = eventData.toString()
        val loadUrlStr = "javascript:healthyup.event(\'$extra\')"
        Log.d(TAG, "loadUrlMsg: $loadUrlStr")
        try {
            webView.loadUrl(loadUrlStr)
        } catch (e: Exception) {
            Log.e(TAG, "Uncaught Reference Error: " + e.message)
        }
    }

    /** Web 에서 함수 호출시 호출됨 **/
    @JavascriptInterface
    fun call(funcName: String, _options: String, transactionId: String) {
        val extra = JsonObject()
        val options = JsonParser.parseString(_options).asJsonObject

        Log.d(
            TAG,
            "[Web Call] API full name: $funcName / options: $_options / transactionId: $transactionId"
        )

        handler.post {
            when (funcName) {
                // TODO: funcName 으로 구분하여 적절한 기능을 제공해야 함
                FunctionName.GET_SERVER_TOKEN -> {
                    extra.addProperty(
                        "serverToken",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJTYW5naGVlIiwiaWF0IjoxNjM2NTU5Mzk5LCJleHAiOjE2NjgwOTUzOTgsImF1ZCI6Ind3dy5leGFtcGxlLmNvbSIsInN1YiI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJ1c2VySWQiOiIxIiwiRW1haWwiOiJ0ZXN0QHRlc3QuY29tIn0.bPS8-InJf3eNcFQ3iZ_KwQrnYijRdZrN9gMkh8aLEsoEPBhEpSL8AmyTVnzEWND-YDyCaUUBx6v_0EIASz6gmA"
                    )
                    onJavaScriptResponse(makeReturnMsg(200, "Success", extra, transactionId))
                }
                FunctionName.START_ACTIVITY -> {
                    targetActivityName.value = options.get("target").asString
                    onJavaScriptResponse(makeReturnMsg(200, "Request Success", extra, transactionId))
                }
            }
        }
    }

    override fun onDestroy() {}

    // TODO: 수정 필요
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

    private object FunctionName {
        const val GET_SERVER_TOKEN = "getServerToken"
        const val START_ACTIVITY = "startActivity"
    }
}