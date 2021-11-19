package com.mashup.healthyup.features.setting

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeExerciseSpeedBinding

class ChangeExerciseSpeedActivity :
    BaseActivity<ActivityChangeExerciseSpeedBinding>(R.layout.activity_change_exercise_speed) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_exercise_speed)
        with(binding) {
            radioGroup.check(binding.radioButton2.id)
            saveButton.isEnabled = false
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.appbar.backButton.setOnClickListener {
            finish()
        }
    }
}