package com.mashup.healthyup.features.setting

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.FragmentChangeCoachVoiceBinding

class ChangeCoachVoiceFragment :
    BaseFragment<FragmentChangeCoachVoiceBinding>(R.layout.fragment_change_coach_voice),
    OnBackPressedListener {

    private val viewModel by activityViewModels<SettingViewModel>()
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        navController = Navigation.findNavController(requireView())
    }

    override fun initViews() {
        super.initViews()
        with(binding) {
            binding.radioGroup.check(binding.btnRadio1.id)
            btnSave.isEnabled = false
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.appbar.btnBack.setOnClickListener {
            navController.navigate(R.id.action_changeCoachVoiceFragment_to_settingMainFragment)
        }
    }

    override fun onBackPressed() {
        navController.navigate(R.id.action_changeCoachVoiceFragment_to_settingMainFragment)
    }
}
