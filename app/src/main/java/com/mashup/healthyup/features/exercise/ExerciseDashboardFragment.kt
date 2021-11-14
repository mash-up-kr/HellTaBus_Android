/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.ActivityExerciseDashboardBinding
import com.mashup.healthyup.features.LauncherViewModel
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

    companion object {

        fun newInstance(): ExerciseDashboardFragment {
            return ExerciseDashboardFragment()
        }
    }
}
