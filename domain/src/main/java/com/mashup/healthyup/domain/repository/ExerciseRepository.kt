/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.entity.Exercise
import com.mashup.healthyup.domain.entity.ExerciseSuggestion

interface ExerciseRepository : Repository {
    suspend fun postExercise(exercise: Exercise): Exercise
    suspend fun getExercise(): List<Exercise>
    suspend fun patchExercise(exercise: Exercise): Exercise
    suspend fun deleteExercise(exercise: Exercise)
    suspend fun getExerciseSuggestion(): ExerciseSuggestion
}
