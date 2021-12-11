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

    val historyListVisible = MutableLiveData(false)
    val historyNone = MutableLiveData<String>()

    private val historyNoneList = listOf(
        "운동할 때 힘이 든 것은 몸이 아닌 마음\n - 김종국 -",
        "운동의 고통은 통증일 뿐 힘든 것이 아니다\n - 김종국 -",
        "운동은 먹는 것까지가 운동이다\n - 김종국 -",
        "운동은 새로운 삶의 시작\n - 김종국 -",
        "헬스클럽은 클럽보다 더 즐거운곳\n - 김종국 -"
    )

    private fun getHistoryNoneItem() {
        val random = Random()
        historyNone.value = historyNoneList[random.nextInt(5)]
    }

    fun onClickSelectedCalenderMonth() {
        _onClickMonth.value = Unit
    }

    private suspend fun getExerciseHistory(date: List<String>): List<ExerciseHistory> {

        val idToken = webPreference.preference.getString(Key.TOKEN, "").toString()
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
            it.exercise.name,
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
                it.exercise.part
                if (index == 0) {
                    a += ExerciseHistoryModel(
                        formattedDate,
                        listOf(it.exercise.part),
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

                            a[i].part = a[i].part + it.exercise.part
                            chack = false
                        }
                    }
                    if (chack) {
                        a += ExerciseHistoryModel(
                            formattedDate,
                            listOf(it.exercise.part),
                            0,
                            listOf(
                                getHistoryExerciseItem(it)
                            )
                        )
                    }
                }
            }

            getHistoryNoneItem()
            historyListVisible.value = a.isNotEmpty()
            _exerciseList.value = a
        }
    }

}
