package com.mashup.healthyup.bridge

import com.google.gson.JsonObject

interface JavaScriptInterfaceCallback {
    fun onJavaScriptResponse(eventData: JsonObject)
    fun onDestroy()
}