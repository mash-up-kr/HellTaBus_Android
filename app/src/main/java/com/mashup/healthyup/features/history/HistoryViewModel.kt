package com.mashup.healthyup.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.healthyup.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : BaseViewModel() {
    private val _exerciseList = MutableLiveData<List<ExerciseModel>>()
    val exerciseList: LiveData<List<ExerciseModel>> = _exerciseList

    init {
        _exerciseList.value = listOf(
            ExerciseModel(2, listOf(1, 3, 4)),
            ExerciseModel(12, listOf(1)),
            ExerciseModel(14, listOf(1, 1, 1, 1, 5)),
            ExerciseModel(22, listOf(1, 3, 4)),
            ExerciseModel(19, listOf(1, 4, 2)),
            ExerciseModel(22, listOf(4, 1, 1))
        )
    }

}
