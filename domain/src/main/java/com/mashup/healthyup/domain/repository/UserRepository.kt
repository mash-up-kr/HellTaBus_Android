/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.entity.AccessToken
import com.mashup.healthyup.domain.entity.User

interface UserRepository : Repository {
    suspend fun register(idToken: String): AccessToken?// 회원가입
    suspend fun signIn(idToken: String): AccessToken? // 로그인
    suspend fun getCurrentUser(idToken: String): User // 현재 로그인 사용자 정보
    suspend fun patchBaseInformation(user: User): User // 회원가입 후 사용자 기본 정보 업데이트
    suspend fun update(user: User): User // 사용자 정보 수정
}
