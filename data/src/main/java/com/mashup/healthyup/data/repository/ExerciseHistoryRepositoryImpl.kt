/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.repository

import com.mashup.healthyup.data.api.ExerciseHistoryApi
import com.mashup.healthyup.data.request.toRequest
import com.mashup.healthyup.domain.entity.ExerciseHistory
import com.mashup.healthyup.domain.repository.ExerciseHistoryRepository
import javax.inject.Inject

class ExerciseHistoryRepositoryImpl @Inject constructor(
    private val historyApi: ExerciseHistoryApi
) : ExerciseHistoryRepository {

    override suspend fun postExerciseHistory(history: ExerciseHistory): ExerciseHistory {
        val request = history.toRequest()
        val response = historyApi.postExerciseHistory(request)
        return response.data ?: ExerciseHistory.EMPTY
    }

    override suspend fun getExerciseHistory(
        from: String?,
        to: String?
    ): List<ExerciseHistory> {
        val response = historyApi.getExerciseHistory(
            from = from,
            to = to
        )

        return response.data.filterNotNull()
    }


}
