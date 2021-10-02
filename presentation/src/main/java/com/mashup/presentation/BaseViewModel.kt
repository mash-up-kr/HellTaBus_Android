/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    
    override fun onCleared() {
        super.onCleared()
        hideLoading()
    }
    
    fun showLoading() {
        _isLoading.value = true
    }
    
    fun hideLoading() {
        _isLoading.value = false
    }
}
