package com.mashup.healthyup.data.response

import com.helltabus.domain.entity.User

data class DumDto(
    val id: Long?,
    val name: String?,
    val age: Int?
) {
    fun toEntity() = User(
        id = this.id ?: 0,
        name = this.name ?: "오형",
        age = this.age ?: 7
    )
}
