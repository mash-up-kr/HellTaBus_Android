package com.mashup.healthyup.features.setting

import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.Key
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.core.Empty
import com.mashup.healthyup.domain.entity.User
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase,
    private val webPreference: WebPreference
) : BaseViewModel() {

    private val _user = flow {
        val idToken = webPreference.preference.getString(Key.TOKEN, String.Empty).toString()
        val user = userUseCase.getUserInfo(idToken)
        emit(user)
    }.stateIn(viewModelScope, SharingStarted.Lazily, User.EMPTY)

    val user: StateFlow<User>
        get() = _user
}
