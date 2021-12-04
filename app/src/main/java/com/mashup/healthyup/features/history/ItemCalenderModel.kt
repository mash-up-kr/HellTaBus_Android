package com.mashup.healthyup.features.history

import com.mashup.healthyup.R

data class ItemCalenderModel(
    val day: Int,
    val status: List<Int>?
) {
    fun getColorId(position: Int): Int? {
        var res = R.color.color_error
        status?.let {
            if (it.size >= position) {
                res = when (it[position - 1]) {
                    1 -> R.color.color_item_calender_1
                    2 -> R.color.color_item_calender_2
                    3 -> R.color.color_item_calender_3
                    4 -> R.color.color_item_calender_4
                    5 -> R.color.color_item_calender_5
                    else -> res
                }
            }
        }
        return res
    }

    fun getVisible(position: Int): Boolean {
        return status?.let {
            position <= it.size
        } ?: false
    }
}