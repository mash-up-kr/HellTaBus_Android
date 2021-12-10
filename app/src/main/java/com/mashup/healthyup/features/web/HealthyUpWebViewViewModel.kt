package com.mashup.healthyup.features.web

import com.mashup.healthyup.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HealthyUpWebViewViewModel @Inject constructor() : BaseViewModel() {
    var backButtonReceiveTarget = WebConstants.Target.ANDROID
}
