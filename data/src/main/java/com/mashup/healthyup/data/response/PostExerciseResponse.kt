/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response

import com.mashup.healthyup.domain.entity.Exercise

data class PostExerciseResponse(
    override val data: Exercise?
) : BaseResponse<Exercise>()
