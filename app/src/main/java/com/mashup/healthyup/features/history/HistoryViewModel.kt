package com.mashup.healthyup.features.history

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mashup.healthyup.Config
import com.mashup.healthyup.Key
import com.mashup.healthyup.base.BaseViewModel
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.domain.entity.ExerciseHistory
import com.mashup.healthyup.domain.usecase.GetExerciseHistoryCase
import com.mashup.healthyup.features.history.model.ExerciseHistoryModel
import com.mashup.healthyup.features.history.model.HistoryExerciseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val exerciseHistoryCase: GetExerciseHistoryCase,
    private val webPreference: WebPreference

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

    private suspend fun getExerciseHistory(date: List<String>): List<ExerciseHistory> {

        val idToken = webPreference.preference.getString(Key.TOKEN, "").toString()
        Log.e("access_key", Config.access_key)
        Log.e("idToken", idToken)
        Log.e("date", date.toString())

        return exerciseHistoryCase.getHistory(date, idToken)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(startTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd-EEE", Locale.KOREAN)
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


    fun loadHistory(date: List<String>) {

        viewModelScope.launch {
            val response = getExerciseHistory(date)
            val a = mutableListOf<ExerciseHistoryModel>()
            response.forEachIndexed { index, it ->
                val formattedDate: String = getDate(it.startTime)
                var chack = true
                if (index == 0) {
                    a += ExerciseHistoryModel(
                        formattedDate,
                        listOf(),
                        0,
                        listOf(
                            getHistoryExerciseItem(it)
                        )
                    )
                } else {
                    for (i in 0 until a.size) {
                        if (i != 0 && a[i].date == formattedDate) {
                            a[i].status += (
                                getHistoryExerciseItem(it)
                                )
                            chack = false
                        }
                    }
                    if (chack) {
                        a += ExerciseHistoryModel(
                            formattedDate,
                            listOf(),
                            0,
                            listOf(
                                getHistoryExerciseItem(it)
                            )
                        )
                    }
                }
            }
            Log.e("a", a.toString())

            _exerciseList.value = a
        }
    }

}
