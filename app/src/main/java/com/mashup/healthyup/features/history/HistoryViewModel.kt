package com.mashup.healthyup.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.features.history.model.ExerciseModel
import com.mashup.healthyup.features.history.model.ExercisePart
import com.mashup.healthyup.features.history.model.HistoryExerciseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : BaseViewModel() {
    private val _exerciseList = MutableLiveData<List<ExerciseModel>>()
    val exerciseList: LiveData<List<ExerciseModel>> = _exerciseList

    private val _onClickMonth = MutableLiveData<Unit>()
    val onClickMonth: LiveData<Unit> = _onClickMonth

    init {
        val a = HistoryExerciseItem(ExercisePart.LEG, 2, 15, "10kg")
        val a2 = HistoryExerciseItem(ExercisePart.ARM, 2, 15, "10kg")
        val a3 = HistoryExerciseItem(ExercisePart.BACK, 2, 15, "10kg")
        val a4 = HistoryExerciseItem(ExercisePart.CHERT, 2, 15, "10kg")
        val a5 = HistoryExerciseItem(ExercisePart.SHOULDER, 2, 15, "10kg")
        _exerciseList.value = listOf(
            ExerciseModel(2, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a3, a3)),
            ExerciseModel(6, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a3)),
            ExerciseModel(12, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a, a, a3)),
            ExerciseModel(22, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a3, a3, a)),
            ExerciseModel(9, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a3, a5, a)),
            ExerciseModel(21, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a2, a3, a4, a5)),
            ExerciseModel(3, "화요일", "하체/어깨", "300KG을 번쩍!✨", listOf(a, a2, a3, a4, a5)),
        )
    }

    fun onClickSelectedCalenderMonth() {
        _onClickMonth.value = Unit
    }
}
