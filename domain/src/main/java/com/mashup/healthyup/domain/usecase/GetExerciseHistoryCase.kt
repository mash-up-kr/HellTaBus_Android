/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.usecase

import com.mashup.healthyup.domain.entity.ExerciseHistory
import com.mashup.healthyup.domain.repository.ExerciseHistoryRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetExerciseHistoryCase(
    private val exerciseHistoryRepository: ExerciseHistoryRepository,
    private val dispatcher: CoroutineDispatcher
) : ParameterizedUseCase<List<String>,List<ExerciseHistory>>(dispatcher) {

    override suspend fun execute(date:List<String>): List<ExerciseHistory> {
        return exerciseHistoryRepository.getExerciseHistory(date[0],date[1])
    }
}
