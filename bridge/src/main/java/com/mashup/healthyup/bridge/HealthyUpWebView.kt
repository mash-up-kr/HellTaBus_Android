package com.mashup.healthyup.bridge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

/** WebView Customizing **/
class HealthyUpWebView : WebView {

    val javaScriptInterface: JavaScriptInterface = JavaScriptInterface(this)
        get() {
            addJavascriptInterface(field, "healthyup_native_api")
            clearCache(true)
            requestFocus()
            return field
        }

    constructor(context: Context) : super(context) {
        initHealthyUpWebView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initHealthyUpWebView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initHealthyUpWebView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initHealthyUpWebView()
    }

    override fun destroy() {
        clearHistory()
        destroyDrawingCache()
        removeAllViews()
        clearCache(true)
        setWebContentsDebuggingEnabled(false)
        super.destroy()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initHealthyUpWebView() {
        webChromeClient = HealthyUpWebChromeClient()
        setWebContentsDebuggingEnabled(true)
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.mediaPlaybackRequiresUserGesture = false

        setBackgroundColor(Color.argb(1, 0, 0, 0))
    }
}