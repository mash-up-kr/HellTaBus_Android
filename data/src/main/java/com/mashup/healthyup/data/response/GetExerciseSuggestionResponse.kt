/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response

import com.mashup.healthyup.domain.entity.Exercise
import com.mashup.healthyup.domain.entity.SuggestionPart

data class GetExerciseSuggestionResponse(
    override val data: ExerciseSuggestion?
) : BaseResponse<ExerciseSuggestion>()

data class ExerciseSuggestion(
    val suggestionPartList: List<SuggestionPart>?,
    val suggestionExerciseList: List<Exercise>?
)
