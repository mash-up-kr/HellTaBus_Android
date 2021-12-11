package com.mashup.healthyup.features.launcher

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.Config
import com.mashup.healthyup.Key
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.di.ApiModule
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase,
    private val webPreference: WebPreference
) : BaseViewModel() {

    sealed class Action {
        object StartLogin : Action()
        object StartHome : Action()
    }

    private val action = Channel<Action>(Channel.BUFFERED)
    val actionFlow: Flow<Action>
        get() = action.receiveAsFlow()

    init {
        checkUserSigned()
    }

    private fun checkUserSigned() {
        viewModelScope.launch {
            delay(1000L)
            if (hasUserInfo().age > 0) {
                action.trySend(Action.StartHome)
            } else {
                action.trySend(Action.StartLogin)
            }
        }
    }

    private suspend fun hasUserInfo(): User {
        val idToken = webPreference.preference.getString(Key.TOKEN, "").toString()
        Config.access_key = idToken
        return userUseCase.getUserInfo(idToken)
    }
}
