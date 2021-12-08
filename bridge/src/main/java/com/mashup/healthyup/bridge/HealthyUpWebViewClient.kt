package com.mashup.healthyup.bridge

import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class HealthyUpWebViewClient : WebViewClient() {

    sealed class Action {
        object RemoveLoadingView : Action()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        action.trySend(Action.RemoveLoadingView)
    }

    companion object {
        val actionFlow: Flow<Action>
            get() = action.receiveAsFlow()
        private val action = Channel<Action>(Channel.BUFFERED)
    }
}