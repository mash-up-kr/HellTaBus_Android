/*
 * Created by Leo on 2021. 10. 03 ..
 */
package com.mashup.presentation.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityExerciseDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseDashboardActivity : BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            binding.circleGraphView.setCueList()
        }

        Glide.with(this)
            .asGif()
            .load(R.raw.gif_exercise_1)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.ivExercisePose)
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java)
            context.startActivity(intent)
        }
    }
}
