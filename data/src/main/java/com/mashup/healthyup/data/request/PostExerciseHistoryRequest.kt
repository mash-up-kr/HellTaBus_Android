/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.request

import com.mashup.healthyup.domain.entity.ExerciseHistory

data class PostExerciseHistoryRequest(
    val userId: Long,
    val exerciseId: Long,
    val startTime: String,
    val finishTime: String
) : Request

fun ExerciseHistory.toRequest(): PostExerciseHistoryRequest {
    return PostExerciseHistoryRequest(
        userId = userId,
        exerciseId = exerciseId,
        startTime = startTime,
        finishTime = finishTime
    )
}
