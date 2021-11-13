/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response.history

import com.mashup.healthyup.data.response.BaseResponse
import com.mashup.healthyup.domain.entity.ExerciseHistory

data class PostExerciseHistoryResponse(
    override val data: ExerciseHistory?
) : BaseResponse<ExerciseHistory>()
