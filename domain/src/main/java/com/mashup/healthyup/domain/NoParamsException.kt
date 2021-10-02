/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup.domain

class NoParamsException(
    override val message: String = "params of usecase is never be null"
) : Exception(message)
