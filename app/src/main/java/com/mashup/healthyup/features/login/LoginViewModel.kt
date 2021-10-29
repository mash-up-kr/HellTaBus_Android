package com.mashup.healthyup.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : com.mashup.healthyup.base.BaseViewModel() {

    private val _onClickLogin = MutableLiveData<com.mashup.healthyup.base.Event<Unit>>()
    val onClickLogin: LiveData<com.mashup.healthyup.base.Event<Unit>> = _onClickLogin

    fun onClickLogin() {
        _onClickLogin.value = com.mashup.healthyup.base.Event(Unit)
    }
}
