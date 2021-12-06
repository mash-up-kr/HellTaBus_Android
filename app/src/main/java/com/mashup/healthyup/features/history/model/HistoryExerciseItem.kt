package com.mashup.healthyup.features.history.model

import com.mashup.healthyup.R

data class HistoryExerciseItem(
    val part: ExercisePart = ExercisePart.LEG,
    val set: Int = 3,
    val count: Int = 15,
    val weight: String = "10kg",
) {
    fun getColorText(): String {
        return "랫풀다운 3세트 | 20kg 20회"
    }

    fun getColorId(): Int {
        return when (part) {
            ExercisePart.LEG -> R.color.color_item_calender_1
            ExercisePart.ARM -> R.color.color_item_calender_2
            ExercisePart.SHOULDER -> R.color.color_item_calender_3
            ExercisePart.CHERT -> R.color.color_item_calender_4
            ExercisePart.BACK -> R.color.color_item_calender_5
            else -> R.color.color_error
        }
    }
}

enum class ExercisePart { LEG, ARM, SHOULDER, CHERT, BACK }
