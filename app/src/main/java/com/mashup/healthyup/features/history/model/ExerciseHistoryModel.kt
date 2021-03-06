package com.mashup.healthyup.features.history.model

data class ExerciseHistoryModel(
    val date: String,
    var part: List<String>,
    val subtitle: Int = 0,
    var status: List<HistoryExerciseItem>
) {
    fun getDate(): List<String> {
        //yyyy-MM-dd-EEE
        return date.split("-")
    }

    fun getDay():String{
        return date.split("-")[2]
    }

    fun getDayOfWeek(): String {
        return date.split("-")[3]
    }

    fun getPart(): String {
        var parts = ""
        part.toSet().forEach { parts += "${getPartName(it)}/" }
        return parts.dropLast(1)
    }

    private fun getPartName(part: String): String {
        return when (part) {
            "LOWER" -> "하체"
            "ARM" -> "팔"
            "BICEPS" -> "팔"
            "TRICEPS" -> "팔"
            "SHOULDER" -> "어깨"
            "CHEST" -> "가슴"
            "BACK" -> "등"
            else -> "팔"
        }
    }

    fun getSubtitle(sum: Int): String = "${sum}KG을 번쩍!✨"

    companion object {
        val EMPTY = ExerciseHistoryModel(
            date = "yyy-MM-dd-EEE",
            part = listOf("하체", "등"),
            subtitle = 100,
            status = listOf(HistoryExerciseItem.EMPTY),
        )
    }
}