package com.mashup.healthyup.data.request

import com.mashup.healthyup.core.Empty

data class Daangn(
    val id: Long
) {
    operator fun invoke(): DaangnDto {
        return DaangnDto(
            id = id.toString(),
            name = String.Empty
        )
    }
}

data class DaangnDto(
    val id: String?,
    val name: String?
)
