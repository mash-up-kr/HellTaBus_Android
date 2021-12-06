package com.mashup.healthyup.features.login

import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.Key
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.domain.entity.AccessToken
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val webPreference: WebPreference,
    private val userUseCase: GetUserUseCase,
) : BaseViewModel() {

    sealed class Action {
        object ClickLogin : Action()
        object StartWebViewSurvey : Action()
        object StartWebViewMain : Action()
    }

    private val channel = Channel<Action>(Channel.BUFFERED)
    val channelFlow: Flow<Action>
        get() = channel.receiveAsFlow()

    fun doOnGoogleLoginSuccess(idToken: String?) {
        //res.isPatched  값이 0이면 회원가입페이지로 1일경우 홈으로
        //res 가 null 로 넘어올 경우에는 "" 을 넘겨주지만 사실상 서버 로그인 실패로 화면 전환 X
        viewModelScope.launch {
            val res = getSignIn(idToken)
            if (res != null) {
                webPreference.apply(Key.TOKEN, "Bearer ${res.accessToken}")
                when (res.isPatched) {
                    1 -> {
                        channel.trySend(Action.StartWebViewMain)
                    }
                    else -> {
                        channel.trySend(Action.StartWebViewSurvey)
                    }
                }
            }
        }
    }

    private suspend fun getSignIn(idToken: String?): AccessToken? {
        return userUseCase.signIn(idToken ?: "")
    }

    fun onClickLogin() {
        channel.trySend(Action.ClickLogin)
    }
}
