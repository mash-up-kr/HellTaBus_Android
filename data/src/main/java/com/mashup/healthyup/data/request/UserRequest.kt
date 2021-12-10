package com.mashup.healthyup.data.request

import com.mashup.healthyup.domain.entity.AudioCoach
import com.mashup.healthyup.domain.entity.Gender
import com.mashup.healthyup.domain.entity.SplitType
import com.mashup.healthyup.domain.entity.Speed
import com.mashup.healthyup.domain.entity.User

data class UserRequest(
    val nickname: String,
    val gender: Gender,
    val age: Int,
    val height: Int,
    val weight: Int, // 몸무게
    val splitType: SplitType, // 사용자 분할 선택
    val audioCoach: AudioCoach?, // 오디오 코치 선택
    val speed: Speed, // 어떤 속도로 운동
    val explanation: Boolean, // 설명 필요 여부
    val createAt: String,
    val updateAt: String,
    val deletedAt: String?
) : Request

fun User.toRequest(): UserRequest {
    return UserRequest(
        nickname = nickname,
        gender = gender ?: Gender.MALE,
        age = age,
        height = height,
        weight = weight,
        splitType = splitType,
        audioCoach = audioCoach,
        speed = speed,
        explanation = explanation,
        createAt = createAt,
        updateAt = updateAt,
        deletedAt = deletedAt
    )
}
