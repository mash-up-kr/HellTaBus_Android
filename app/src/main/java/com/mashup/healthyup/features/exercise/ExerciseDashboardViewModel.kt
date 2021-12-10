/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.domain.entity.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDashboardViewModel @Inject constructor(
    private val params: Params
) : BaseViewModel() {

    data class Params(val exerciseList: List<Exercise>, val todayDate: String)

    data class State(
        val exerciseList: List<Exercise>,
        val todayDate: String,
        val onAudioPlaying: Boolean = true,
        val index: Int = 0, // 현재 몇 세트 인지
        val isPaused: Boolean = false
    ) {
        val stage: Exercise
            get() = exerciseList[index]
    }

    private val _state = MutableStateFlow(State(params.exerciseList, params.todayDate))
    val state: StateFlow<State>
        get() = _state

    init {
        if (params.exerciseList.isEmpty())
            throw ExerciseException.ExerciseListEmptyException()
    }

    fun onAudioEnd() {
        viewModelScope.launch {
            delay(250L)
            val currentState = _state.value
            _state.emit(currentState.copy(onAudioPlaying = false))
        }
    }

    fun onPauseClick() {
        viewModelScope.launch {
            val currentState = _state.value
            _state.emit(currentState.copy(isPaused = !currentState.isPaused))
        }
    }
}
