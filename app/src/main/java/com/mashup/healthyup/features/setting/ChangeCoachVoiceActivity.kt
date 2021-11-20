package com.mashup.healthyup.features.setting

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeCoachVoiceBinding

class ChangeCoachVoiceActivity :
    BaseActivity<ActivityChangeCoachVoiceBinding>(R.layout.activity_change_coach_voice) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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