package com.mashup.healthyup.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _onClickLogin = MutableLiveData<Event<Unit>>()
    val onClickLogin: LiveData<Event<Unit>> = _onClickLogin

    fun onClickLogin() {
        _onClickLogin.value = Event(Unit)
    }
}
