package com.mashup.healthyup.features.setting

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel by viewModels<SettingViewModel>()

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
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
