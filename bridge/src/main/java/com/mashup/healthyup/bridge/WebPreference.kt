/*
 * Created by Leo on 2021. 12. 04 ..
 */
package com.mashup.healthyup.bridge

import android.content.SharedPreferences

interface WebPreference {
    val preference: SharedPreferences
    operator fun <T : Any> set(key: String, value: T?)
    fun getInt(key: String, defaultValue: Int): Int
    fun getString(key: String, defaultValue: String): String
}
