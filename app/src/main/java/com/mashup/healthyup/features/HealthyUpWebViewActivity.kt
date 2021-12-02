package com.mashup.healthyup.features

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.JavaScriptInterface
import com.mashup.healthyup.databinding.ActivityHealthyUpWebViewBinding
import com.mashup.healthyup.features.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HealthyUpWebViewActivity :
    BaseActivity<ActivityHealthyUpWebViewBinding>(R.layout.activity_healthy_up_web_view) {

    private lateinit var javaScriptInterface: JavaScriptInterface
    private val viewModel by viewModels<HealthyUpWebViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            javaScriptInterface = binding.healthyUpWebView.javaScriptInterface
            binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
        }
        observeTargetActivityName()
    }

    private fun observeTargetActivityName() {
        javaScriptInterface.targetActivityName.observe(this, {
            when (it) {
                "setting" -> {
                    startActivity(
                        Intent(
                            this,
                            SettingActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                }
                "history" -> {
                    startActivity(
                        Intent(
                            this,
                            LauncherActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    )
                }
            }
        })
    }
}