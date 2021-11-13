/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.request

import com.mashup.healthyup.domain.entity.Exercise

data class PostExerciseRequest(
    val name: String,
    val priority: Int,
    val part: String,
    val baseCount: Int,
    val setCount: Int,
    val startWeight: Int,
    val changeWeight: Int,
    val setBreakTime: Int,
    val breakTime: Int,
    val imageLink: String
) : Request

fun Exercise.toRequest(): PostExerciseRequest {
    return PostExerciseRequest(
        name = name,
        priority = priority,
        part = part,
        baseCount = baseCount,
        setCount = setCount,
        startWeight = startWeight,
        changeWeight = changeWeight,
        setBreakTime = setBreakTime,
        breakTime = breakTime,
        imageLink = imageLink
    )
}
