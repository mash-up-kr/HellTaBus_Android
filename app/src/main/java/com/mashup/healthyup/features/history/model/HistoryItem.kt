package com.mashup.healthyup.features.history.model

import com.mashup.healthyup.R

data class HistoryItem(
    val day: Int,
    val dayOfWeek: String = "화요일",
    val part: String = "하체/어깨",
    val subtitle: String = "300KG을 번쩍!✨",
    val status: List<HistoryExerciseItem>?
) {
    fun getColorId(position: Int): Int {
        var res = R.color.color_error
        status?.let {
            if (it.size >= position) {
                res = it[position - 1].getColorId()
            }
        }
        return res
    }

    fun getVisible(position: Int): Boolean {
        return status?.let {
            position <= it.size
        } ?: false
    }

    fun getModel(position: Int): HistoryExerciseItem? {
        return status?.let {
            if (it.size >= position) {
                status[position - 1]
            } else null
        }
    }
}