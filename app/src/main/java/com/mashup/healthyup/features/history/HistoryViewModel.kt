package com.mashup.healthyup.features.history

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.domain.entity.ExerciseHistory
import com.mashup.healthyup.domain.usecase.GetExerciseHistoryCase
import com.mashup.healthyup.domain.usecase.GetUserUseCase
import com.mashup.healthyup.features.history.model.ExerciseHistoryModel
import com.mashup.healthyup.features.history.model.HistoryExerciseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val exerciseHistoryCase: GetExerciseHistoryCase,
    private val userUseCase: GetUserUseCase
) : BaseViewModel() {
    private val _exerciseList = MutableLiveData<List<ExerciseHistoryModel>>()
    val exerciseHistoryList: LiveData<List<ExerciseHistoryModel>> = _exerciseList

    private val _onClickMonth = MutableLiveData<Unit>()
    val onClickMonth: LiveData<Unit> = _onClickMonth

    init {
        _exerciseList.value = listOf(
            ExerciseHistoryModel.EMPTY,
            ExerciseHistoryModel.EMPTY,
            ExerciseHistoryModel.EMPTY,
            ExerciseHistoryModel.EMPTY,
            ExerciseHistoryModel.EMPTY
        )
    }

    fun onClickSelectedCalenderMonth() {
        _onClickMonth.value = Unit
    }

//    private suspend fun getExerciseHistory(): Result<List<ExerciseHistory>> {
//        val a = listOf<String>("2021-10-01", "2021-11-01")
//        return exerciseHistoryCase.invoke(a)
//    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(startTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd-EEE.", Locale.KOREAN)
        val date: Date = inputFormat.parse(startTime)
        return outputFormat.format(date)
    }

    private fun getHistoryExerciseItem(it: ExerciseHistory): HistoryExerciseItem {
        return HistoryExerciseItem(
            it.exercise.part,
            it.exercise.setCount,
            it.exercise.baseCount,
            (it.exercise.startWeight + it.exercise.changeWeight)
        )
    }


//    fun loadHistory() {
//        viewModelScope.launch {
//            val response = getExerciseHistory()
//            if (response.isSuccess) {
//                val a = mutableListOf<ExerciseHistoryModel>()
//                response.getOrNull()?.forEach { it ->
//                    val formattedDate: String = getDate(it.startTime)
//                    for (i in 0 until a.size) {
//                        if (i != 0 && a[i].date == formattedDate) {
//                            a[i].status += (
//                                getHistoryExerciseItem(it)
//
//                                )
//                        } else {
//                            a += ExerciseHistoryModel(
//                                formattedDate,
//                                listOf(),
//                                0,
//                                listOf(
//                                    getHistoryExerciseItem(it)
//                                )
//                            )
//                        }
//                    }
//                }
//                _exerciseList.value = a
//            } else {
//                _exerciseList.value = listOf()
//
//            }
//        }
//    }

}
