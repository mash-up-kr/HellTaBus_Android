package com.mashup.healthyup.features.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.FragmentSettingMainBinding
import com.mashup.healthyup.features.web.HealthyUpWebViewActivity
import com.mashup.healthyup.features.web.WebConstants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

interface OnBackPressedListener {
    fun onBackPressed()
}

class SettingMainFragment :
    BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main),
    OnBackPressedListener {

    private val viewModel by activityViewModels<SettingViewModel>()
    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        navController = Navigation.findNavController(requireView())
        initListeners()
    }

    override fun initListeners() {
        super.initListeners()

        binding.btnChangeProfile.setOnClickListener {
            navController.navigate(R.id.action_settingMainFragment_to_changeProfileFragment)
        }

        binding.btnSplitSetting.setOnClickListener {
            HealthyUpWebViewActivity.start(
                context = requireContext(),
                loadUrl = WebConstants.URL.SPLIT,
                action = { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP }
            )
            activity?.overridePendingTransition(0, 0);
        }

        binding.btnChangeCoachVoice.setOnClickListener {
            navController.navigate(R.id.action_settingMainFragment_to_changeCoachVoiceFragment)
        }

        binding.btnChangeExerciseSpeed.setOnClickListener {
            navController.navigate(R.id.action_settingMainFragment_to_changeExerciseSpeedFragment)
        }

        binding.btnChangeExplanationLength.setOnClickListener {
            navController.navigate(R.id.action_settingMainFragment_to_changeExplanationLengthFragment)
        }
        binding.appbar.btnClose.setOnClickListener {
            activity?.finish()
        }
    }

    override fun initObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collect { user ->
                binding.btnSplitSetting.text = user.splitType.value
            }
        }
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@SettingMainFragment).commit()
        }
    }

    override fun onBackPressed() {
        closeFragment()
    }
}
