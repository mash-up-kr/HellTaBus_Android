/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.mashup.healthyup

import kotlinx.coroutines.CoroutineDispatcher

interface ExecutorProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}
