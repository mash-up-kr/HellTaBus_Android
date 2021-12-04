package com.mashup.healthyup.bridge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebView

/** WebView Customizing **/
class HealthyUpWebView : WebView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun destroy() {
        clearHistory()
        destroyDrawingCache()
        removeAllViews()
        clearCache(true)
        setWebContentsDebuggingEnabled(false)
        super.destroy()
    }

    fun setJavaScriptInterface(preference: WebPreference) {
        val javaScriptInterface = JavaScriptInterface(this, preference)
        addJavascriptInterface(javaScriptInterface, "healthyup_native_api")
        clearCache(true)
        requestFocus()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        webChromeClient = HealthyUpWebChromeClient()
        setWebContentsDebuggingEnabled(true)
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.mediaPlaybackRequiresUserGesture = false

        setBackgroundColor(Color.argb(1, 0, 0, 0))
    }
}
