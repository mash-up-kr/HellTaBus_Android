/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.Key
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.ActivityExerciseDashboardBinding
import com.mashup.healthyup.databinding.LayoutProgressBinding
import com.mashup.healthyup.features.launcher.LauncherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDashboardFragment :
    BaseFragment<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    private val launcherViewModel by activityViewModels<LauncherViewModel>()
    private val viewModel by viewModels<ExerciseDashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
            val progressView = LayoutProgressBinding.inflate(LayoutInflater.from(context)).root
            binding.layoutProgress.addView(progressView, params)
        }
    }

    companion object {

        fun newInstance(exerciseId: Long): ExerciseDashboardFragment {
            return ExerciseDashboardFragment().apply {
                arguments = bundleOf(
                    Key.EXERCISE_ID to exerciseId
                )
            }
        }
    }
}
