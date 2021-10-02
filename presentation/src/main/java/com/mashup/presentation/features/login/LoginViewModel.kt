/*
 * Created by Leo on 2021. 10. 02 ..
 */
package com.mashup.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.presentation.base.BaseViewModel
import com.mashup.presentation.base.Event
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
