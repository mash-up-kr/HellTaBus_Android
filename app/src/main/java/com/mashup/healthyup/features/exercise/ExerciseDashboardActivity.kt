/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityExerciseDashboardBinding
import com.mashup.healthyup.databinding.LayoutProgressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDashboardActivity :
    BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    private val viewModel by viewModels<ExerciseDashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            binding.graphView.setCueList()
        }
    }

    override fun initViews() {
        // TODO : layout_progress 동적으로 addView
        addProgressViews()
    }

    private fun addProgressViews() {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.weight = 1f

        listOf(0, 1, 2).forEach { _ ->
            val progressView = LayoutProgressBinding.inflate(layoutInflater).root
            binding.layoutProgress.addView(progressView, params)
        }
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, ExerciseDashboardActivity::class.java)
        }

        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
