package com.mashup.healthyup.features.setting

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.FragmentChangeExerciseSpeedBinding

class ChangeExerciseSpeedFragment :
    BaseFragment<FragmentChangeExerciseSpeedBinding>(R.layout.fragment_change_exercise_speed),
    OnBackPressedListener {

    private val viewModel by viewModels<SettingViewModel>()
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        navController = Navigation.findNavController(requireView())
    }

    override fun initViews() {
        super.initViews()
        with(binding) {
            radioGroup.check(binding.btnRadio2.id)
            btnSave.isEnabled = false
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.appbar.btnBack.setOnClickListener {
            navController.navigate(R.id.action_changeExerciseSpeedFragment_to_settingMainFragment)
        }
    }

    override fun onBackPressed() {
        navController.navigate(R.id.action_changeExerciseSpeedFragment_to_settingMainFragment)
    }
}
