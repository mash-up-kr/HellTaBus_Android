package com.mashup.healthyup.features

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor() : com.mashup.healthyup.base.BaseViewModel() {

    // TODO : 로그인 유무 체크, 로그인 되어 있으면, 홈화면으로 이동, 로그인 안되어 있으면, 로그인 화면으로 이동
}
