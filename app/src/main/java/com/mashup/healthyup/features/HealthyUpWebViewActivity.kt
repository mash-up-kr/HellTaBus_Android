package com.mashup.healthyup.features

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.JavaScriptInterface
import com.mashup.healthyup.bridge.WebAPIController
import com.mashup.healthyup.bridge.WebAPIController.FunctionName
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
//            binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
            binding.healthyUpWebView.loadUrl("http://172.30.1.6:3000/test/custom")
        }
        observeWebRequest()
    }

    private fun observeWebRequest() {
        WebAPIController.requestFromWeb.observe(this, {
            when (it.get("functionName").asString) {
                FunctionName.START_ACTIVITY -> {
                    startActivityFromWeb(it)
                }
            }
        })
    }

    private fun startActivityFromWeb(options: JsonObject) {
        when (options.get("target").asString) {
            "setting" -> {
                startActivity(
                    Intent(
                        this@HealthyUpWebViewActivity,
                        SettingActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
            }
            "history" -> {
                startActivity(
                    Intent(
                        this@HealthyUpWebViewActivity,
                        LauncherActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
            }
            "exercise" -> {
                // TODO: 웹으로부터 받은 데이터 저장 추가 필요
                val exerciseArray = options.get("exerciseList").asJsonPrimitive
                Log.d("HealthyUpWebViewActivity", "exerciseArray: $exerciseArray")
                startActivity(
                    Intent(
                        this@HealthyUpWebViewActivity,
                        ExerciseSummaryActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
            }
        }
    }
}