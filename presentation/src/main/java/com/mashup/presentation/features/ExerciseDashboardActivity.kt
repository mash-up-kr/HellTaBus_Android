/*
 * Created by Leo on 2021. 10. 03 ..
 */
package com.mashup.presentation.features

import android.content.Context
import android.content.Intent
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityExerciseDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDashboardActivity : BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java)
            context.startActivity(intent)
        }
    }
}
