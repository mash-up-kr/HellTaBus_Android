/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.entity.ExerciseHistory

interface ExerciseHistoryRepository : Repository {
    suspend fun postExerciseHistory(history: ExerciseHistory): ExerciseHistory
    suspend fun getExerciseHistory(
        ids: List<Long>,
        duration: String?,
        from: String?,
        to: String?
    ): List<ExerciseHistory>
}
