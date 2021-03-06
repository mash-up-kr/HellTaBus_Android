/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.core

import android.content.Context
import android.widget.Toast

val Context.dp4
    get() = resources.getDimensionPixelSize(R.dimen.space_4)

val Context.dp5
    get() = resources.getDimensionPixelSize(R.dimen.space_5)

val Context.dp8
    get() = resources.getDimensionPixelSize(R.dimen.space_8)

val Context.dp10
    get() = resources.getDimensionPixelSize(R.dimen.space_10)

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
