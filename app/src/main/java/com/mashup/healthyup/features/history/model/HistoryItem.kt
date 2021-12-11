package com.mashup.healthyup.features.history.model

import com.mashup.healthyup.R

data class HistoryItem(
    val day: String,
    val dayOfWeek: String = "화요일",
    val part: String = "하체/어깨",
    val weightSum: Int,
    val status: List<HistoryExerciseItem>?
) {
    companion object {
        val EMPTY = HistoryItem(
            day = "1",
            dayOfWeek = "화요일",
            part = "하체/어깨",
            weightSum = 100,
            status = listOf(HistoryExerciseItem.EMPTY),
        )
    }

    fun getWeightIcon(): String {
        return when {
            weightSum > 300 -> {
                "🐷"
            }
            weightSum > 200 -> {
                "\uD83D\uDC34"
            }
            weightSum > 100 -> {
                "\uD83D\uDC3C"
            }
            else -> ""
        }
    }

    val getSubtitle = "${weightSum}KG을 번쩍!✨"

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