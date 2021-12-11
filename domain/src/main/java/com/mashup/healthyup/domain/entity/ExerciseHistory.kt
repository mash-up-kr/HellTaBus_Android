/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.entity

data class ExerciseHistory(
    val id: Long,
    val userId: Long,
    val exerciseId: Long,
    val startTime: String,
    val finishTime: String,
    val exercise: Exercise
) : Entity {
    companion object {
        val EMPTY = ExerciseHistory(
            id = 0,
            userId = 0,
            exerciseId = 0,
            startTime = "",
            finishTime = "",
            exercise = Exercise.EMPTY,
        )
    }
}
