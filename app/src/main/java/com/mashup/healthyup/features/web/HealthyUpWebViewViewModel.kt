package com.mashup.healthyup.features.web

import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.features.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HealthyUpWebViewViewModel @Inject constructor() : BaseViewModel() {
    var backButtonReceiveTarget = WebConstants.Target.ANDROID
}
