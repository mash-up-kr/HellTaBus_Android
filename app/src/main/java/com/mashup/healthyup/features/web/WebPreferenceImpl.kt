/*
 * Created by Leo on 2021. 12. 04 ..
 */
package com.mashup.healthyup.features.web

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.mashup.healthyup.bridge.WebPreference

class WebPreferenceImpl constructor(private val applicationContext: Context) : WebPreference {

    override val preference: SharedPreferences
        get() = applicationContext.getSharedPreferences("", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor
        get() = preference.edit()

    private val gson: Gson by lazy { Gson() }

    override fun <T : Any> set(key: String, value: T?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            is Parcelable -> edit { it.putString(key, gson.toJson(value)) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

//    private inline fun <reified T : Any> getInternals(key: String, defaultValue: T?): T? {
//        return when (T::class) {
//            String::class -> preference.getString(key, defaultValue as? String) as T?
//            Int::class -> preference.getInt(key, defaultValue as? Int ?: -1) as T?
//            Boolean::class -> preference.getBoolean(key, defaultValue as? Boolean ?: false) as T?
//            Float::class -> preference.getFloat(key, defaultValue as? Float ?: -1f) as T?
//            Long::class -> preference.getLong(key, defaultValue as? Long ?: -1) as T?
//            else -> {
//                val stringValue = preference.getString(key, null)
//                stringValue?.let { getGson().fromJson(it, T::class.java) }
//            }
//        }
//    }

    private inline fun edit(operation: (SharedPreferences.Editor) -> Unit) {
        operation(editor)
        editor.apply()
    }
}
