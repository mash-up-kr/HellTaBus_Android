package com.mashup.healthyup.features.login

import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.bridge.WebPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val webPreference: WebPreference
) : BaseViewModel() {

    sealed class Action {
        object ClickLogin : Action()
        data class TokenSaved(val idToken: String?) : Action()
    }

    private val channel = Channel<Action>(Channel.BUFFERED)
    val channelFlow: Flow<Action>
        get() = channel.receiveAsFlow()

    fun doOnGoogleLoginSuccess(idToken: String?) {
        viewModelScope.launch {
            webPreference.apply("token", idToken)
            channel.trySend(Action.TokenSaved(idToken))
        }
    }

    fun onClickLogin() {
        channel.trySend(Action.ClickLogin)
    }
}
