package com.mashup.healthyup.bridge

import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import com.google.gson.JsonObject
import org.json.JSONObject

/** WebView 에 올라온 URL 의 JavaScript 와 통신하는 부분 **/
class JavaScriptInterface(private val webView: HealthyUpWebView?) : JavaScriptInterfaceCallback {

    private val TAG = javaClass.simpleName
    private val handler = Handler()

    override fun onJavaScriptResponse(eventData: JsonObject) {
        val extra = eventData.toString()
        val loadUrlStr = "javascript:healthyup.event(\'$extra\')"
        Log.d(TAG, "loadUrlMsg: $loadUrlStr")
        try {
            webView?.loadUrl(loadUrlStr) ?: Log.d(TAG, "WebView is destroyed!")
        } catch (e: Exception) {
            Log.e(TAG, "Uncaught Reference Error: " + e.message)
        }
    }

    /** Web 에서 함수 호출시 호출됨 **/
    @JavascriptInterface
    fun call(funcName: String, options: String, transactionId: String) {
        // TODO: funcName 으로 구분하여 적절한 기능을 제공해야 함
        handler.post {
            Log.d(
                TAG,
                "[Web Call] API full name: $funcName / options: $options / transactionId: $transactionId"
            )
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
}