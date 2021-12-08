package com.mashup.healthyup.features.setting

import android.content.Context
import android.content.Intent
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun initListeners() {
        super.initListeners()
        binding.btnChagneProfile.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChangeProfileActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        }
        binding.btnChangeCoachVoice.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChangeCoachVoiceActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        }
        binding.btnChangeExerciseSpeed.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChangeExerciseSpeedActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        }
        binding.btnChangeExplanationLength.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChangeExplanationLengthActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
        }
        binding.appbar.btnClose.setOnClickListener {
            finish()
        }
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }

        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, SettingActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
