package com.mashup.healthyup.features.history.model

data class ExerciseModel(
    val day: Int,
    val dayOfWeek: String = "화요일",
    val part: String = "하체/어깨",
    val subtitle: String = "300KG을 번쩍!✨",
    val status: List<HistoryExerciseItem>?
)