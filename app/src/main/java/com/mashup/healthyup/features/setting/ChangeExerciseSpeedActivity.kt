package com.mashup.healthyup.features.setting

import android.os.Bundle
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityChangeCoachVoiceBinding

class ChangeExerciseSpeedActivity :
    BaseActivity<ActivityChangeCoachVoiceBinding>(R.layout.activity_change_exercise_speed) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_exercise_speed)
    }
}