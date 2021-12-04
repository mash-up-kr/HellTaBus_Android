package com.mashup.healthyup.features.history

import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("intText")
    fun setIntToText(view: TextView, text: Int?) {
        view.text = text.toString()
    }

    @JvmStatic
    @BindingAdapter("imageTint")
    fun setImageTint(view: ImageView, id: Int?) {
        Log.e("12312", id.toString())
        id?.let {
            view.setColorFilter(view.context.getColor(id), PorterDuff.Mode.SRC_ATOP)
            view
        }
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}