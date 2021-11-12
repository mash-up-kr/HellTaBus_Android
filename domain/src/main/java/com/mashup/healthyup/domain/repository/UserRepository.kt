/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.Result
import com.mashup.healthyup.domain.entity.User

interface UserRepository : Repository {
    suspend fun register() // 회원가입
    suspend fun signIn() // 로그인
    suspend fun getCurrentUser(): Result<User> // 현재 로그인 사용자 정보
    suspend fun patchBaseInformation(user: User): Result<User> // 회원가입 후 사용자 기본 정보 업데이트
    suspend fun update(user: User): Result<User> // 사용자 정보 수정
}
