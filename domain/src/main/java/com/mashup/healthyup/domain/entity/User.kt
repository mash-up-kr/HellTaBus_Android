package com.mashup.healthyup.domain.entity

data class User(
    val id: Long,
    val nickname: String,
    val email: String,
    val googleAccount: String,
    val gender: Gender?,
    val age: Int,
    val height: Int,
    val weight: Int, // 몸무게
    val healthStyle: HealthStyle, // 사용자 분할 선택
    val audioCoach: AudioCoach?, // 오디오 코치 선택
    val speed: Speed, // 어떤 속도로 운동
    val explanation: Int // 설명 필요 여부
) : Entity {

    companion object {
        val EMPTY = User(
            id = 0,
            nickname = "",
            email = "",
            googleAccount = "",
            gender = null,
            age = 0,
            height = 0,
            weight = 0,
            healthStyle = HealthStyle.FULL_BODY_WORKOUT,
            audioCoach = null,
            speed = Speed.FAST,
            explanation = 0
        )
    }
}
