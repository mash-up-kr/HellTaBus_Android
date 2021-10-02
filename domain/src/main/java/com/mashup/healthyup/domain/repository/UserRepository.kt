/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain.repository

import com.mashup.healthyup.domain.Result
import com.mashup.healthyup.domain.entity.User

interface UserRepository : Repository {
    suspend fun getUser(userId: Long): Result<User>
    suspend fun postUser(userId: Long): Result<User>
}
