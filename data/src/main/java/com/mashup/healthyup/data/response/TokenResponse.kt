/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.data.response

import com.mashup.healthyup.data.request.IdToken

data class TokenResponse(
    override val data: IdToken?
) : BaseResponse<IdToken>()
