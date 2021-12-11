package com.mashup.healthyup.features.history.model

import com.mashup.healthyup.R

data class HistoryExerciseItem(
    val name: String,
    val part: String,
    val set: Int,
    val count: Int,
    val weight: Int
) {
    fun getNameSetWeight(): String {
        return "$name ${set}세트 | ${weight}kg ${count}회"
    }

    fun getColorId(): Int {
        return when (setPart(part)) {
            ExercisePart.LOWER -> R.color.color_item_calender_1
            ExercisePart.ARM -> R.color.color_item_calender_2
            ExercisePart.SHOULDER -> R.color.color_item_calender_3
            ExercisePart.CHEST -> R.color.color_item_calender_4
            ExercisePart.BACK -> R.color.color_item_calender_5
            else -> R.color.color_error
        }
    }

    private fun setPart(part: String): ExercisePart {
        return when (part) {
            "LOWER" -> ExercisePart.LOWER
            "ARM" -> ExercisePart.ARM
            "BICEPS" -> ExercisePart.ARM
            "TRICEPS" -> ExercisePart.ARM
            "SHOULDER" -> ExercisePart.SHOULDER
            "CHEST" -> ExercisePart.CHEST
            "BACK" -> ExercisePart.BACK
            else -> ExercisePart.ARM
        }
    }

    companion object {
        val EMPTY = HistoryExerciseItem(
            name = "런지",
            part = "LOWER",
            set = 3,
            count = 15,
            weight = 10,
        )
    }
}

enum class ExercisePart { LOWER, ARM, SHOULDER, CHEST, BACK }
