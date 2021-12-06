package com.mashup.healthyup.data.response.user

import com.mashup.healthyup.data.response.BaseResponse
import com.mashup.healthyup.domain.entity.User

data class UserResponse(
    override val data: User?
) : BaseResponse<User>()
