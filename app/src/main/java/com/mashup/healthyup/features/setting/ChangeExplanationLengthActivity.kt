package com.mashup.healthyup.features.setting

import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeExplanationLengthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeExplanationLengthActivity :
    BaseActivity<ActivityChangeExplanationLengthBinding>(R.layout.activity_change_explanation_length) {

    override fun initViews() {
        super.initViews()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_explanation_length)
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
