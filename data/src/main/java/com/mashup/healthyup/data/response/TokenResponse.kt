/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response

import com.mashup.healthyup.domain.entity.AccessToken

data class TokenResponse(
    override val data: AccessToken?
) : BaseResponse<AccessToken>()