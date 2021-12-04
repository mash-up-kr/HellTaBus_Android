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
        if (id != 0 && id !=null) {
            view.setColorFilter(view.context.getColor(id), PorterDuff.Mode.SRC_ATOP)
        }
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}