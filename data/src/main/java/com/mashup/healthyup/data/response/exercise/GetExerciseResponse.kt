/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response.exercise

import com.mashup.healthyup.data.response.BaseResponse
import com.mashup.healthyup.domain.entity.Exercise

data class GetExerciseResponse(
    override val data: List<Exercise>?
) : BaseResponse<List<Exercise>>()
