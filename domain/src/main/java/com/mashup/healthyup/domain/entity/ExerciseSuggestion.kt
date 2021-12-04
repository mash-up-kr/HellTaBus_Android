/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.domain.entity

data class ExerciseSuggestion(
    val parts: List<SuggestionPart>,
    val exerciseList: List<Exercise>
) : Entity
