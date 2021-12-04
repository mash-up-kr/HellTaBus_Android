package com.mashup.healthyup.bridge

import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/** WebView 에 올라온 URL 의 JavaScript 와 통신하는 부분 **/
class JavaScriptInterface(private val webView: HealthyUpWebView) : JavaScriptInterfaceCallback {

    private val TAG = javaClass.simpleName
    private val handler = Handler()

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
        val options = JsonParser.parseString(_options).asJsonObject
        Log.d(TAG, "[Web Call] API full name: $funcName / options: $_options / transactionId: $transactionId")
        handler.post {
            WebAPIController.requestAPI(funcName, options, transactionId, this)
        }
    }

    override fun onDestroy() {}
}