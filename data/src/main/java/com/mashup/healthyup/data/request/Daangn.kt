package com.mashup.healthyup.data.request

data class Daangn(
    val id: Long
) {
    operator fun invoke(): DaangnDto {
        return DaangnDto(
            id = id.toString(),
            name = ""
        )
    }
}

data class DaangnDto(
    val id: String?,
    val name: String?
)
