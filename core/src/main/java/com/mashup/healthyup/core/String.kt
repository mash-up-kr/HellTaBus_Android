/*
 * Created by Leo on 2021. 10. 02 ..
 */
package com.mashup.healthyup.core

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

val String.Companion.Empty
    get() = ""

val String.Companion.Space
    get() = " "

fun getForegroundBoldSpan(
    context: Context,
    @ColorRes colorRes: Int,
    totalString: String,
    coloredString: String
): SpannableStringBuilder {
    val sb = SpannableStringBuilder(totalString)
    try {
        val color = ContextCompat.getColor(context, colorRes)
        val subStringColorSpan = ForegroundColorSpan(color)
        val start = totalString.indexOf(coloredString)
        val end = coloredString.length
        sb.setSpan(
            subStringColorSpan,
            start, start + end, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            start + end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    } catch (e: Exception) {
        /* explicitly empty */
    }

    return sb
}
