package com.mashup.healthyup.features.summary

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mashup.healthyup.R

class ExerciseSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_summary)
        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor =
                ContextCompat.getColor(this@ExerciseSummaryActivity, R.color.color_primary_variant3)
        }
    }
}
