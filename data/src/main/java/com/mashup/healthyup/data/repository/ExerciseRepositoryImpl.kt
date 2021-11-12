/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.repository

import com.mashup.healthyup.data.api.ExerciseApi
import com.mashup.healthyup.data.request.toRequest
import com.mashup.healthyup.domain.entity.Exercise
import com.mashup.healthyup.domain.entity.ExerciseSuggestion
import com.mashup.healthyup.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseApi: ExerciseApi
) : ExerciseRepository {

    override suspend fun postExercise(exercise: Exercise): Exercise {
        val response = exerciseApi.postExercise(exercise.toRequest())
        return response.data ?: Exercise.EMPTY
    }

    override suspend fun getExercise(): List<Exercise> {
        val response = exerciseApi.getExercise()
        return response.data ?: emptyList()
    }

    override suspend fun patchExercise(exercise: Exercise): Exercise {
        val response = exerciseApi.patchExercise(exercise.id)
        return response.data ?: Exercise.EMPTY
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        return exerciseApi.deleteExercise(exercise.id)
    }

    override suspend fun getExerciseSuggestion(): ExerciseSuggestion {
        val response = exerciseApi.getExerciseSuggestion()
        val parts = response.data?.suggestionPartList ?: emptyList()
        val exerciseList = response.data?.suggestionExerciseList ?: emptyList()

        return ExerciseSuggestion(parts, exerciseList)
    }
}
