/*
 * Created by Leo on 2021. 12. 10 ..
 */
package com.mashup.healthyup.core

import android.view.View

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
