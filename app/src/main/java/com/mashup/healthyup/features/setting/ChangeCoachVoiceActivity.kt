package com.mashup.healthyup.features.setting

import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeCoachVoiceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeCoachVoiceActivity :
    BaseActivity<ActivityChangeCoachVoiceBinding>(R.layout.activity_change_coach_voice) {

    override fun initViews() {
        super.initViews()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_coach_voice)
        with(binding) {
            radioGroup.check(binding.btnRadio1.id)
            btnSave.isEnabled = false
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.appbar.btnBack.setOnClickListener {
            finish()
        }
    }
}
