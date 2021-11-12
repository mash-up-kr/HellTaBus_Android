package com.mashup.healthyup.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.base.Event
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usecase: GetUserUseCase
) : BaseViewModel() {

    private val _onClickLogin = MutableLiveData<Event<Unit>>()
    val onClickLogin: LiveData<Event<Unit>> = _onClickLogin

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                usecase.invoke()
            }.onSuccess { result ->
                val user = result.getOrThrow()
                // TODO: user 객체 사용

            }.onFailure { error ->
                error.message
            }
        }
    }

    fun onClickLogin() {
        _onClickLogin.value = Event(Unit)
    }
}
