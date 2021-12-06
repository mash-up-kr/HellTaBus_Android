package com.mashup.healthyup.data.response

import com.mashup.healthyup.domain.entity.User

data class UserResponse(
    override val data: User?
) : BaseResponse<User>()
