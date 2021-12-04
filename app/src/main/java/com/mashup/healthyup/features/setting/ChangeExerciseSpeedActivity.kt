package com.mashup.healthyup.features.setting

import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeExerciseSpeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeExerciseSpeedActivity :
    BaseActivity<ActivityChangeExerciseSpeedBinding>(R.layout.activity_change_exercise_speed) {

    override fun initViews() {
        super.initViews()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_exercise_speed)
        with(binding) {
            radioGroup.check(binding.btnRadio2.id)
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
