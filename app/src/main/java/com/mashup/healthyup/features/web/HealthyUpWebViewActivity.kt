package com.mashup.healthyup.features.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.JsonObject
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.JavaScriptInterface
import com.mashup.healthyup.bridge.WebAPIController
import com.mashup.healthyup.bridge.WebAPIController.FunctionName
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.databinding.ActivityHealthyUpWebViewBinding
import com.mashup.healthyup.features.launcher.LauncherActivity
import com.mashup.healthyup.features.setting.SettingActivity
import com.mashup.healthyup.features.summary.ExerciseSummaryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HealthyUpWebViewActivity :
    BaseActivity<ActivityHealthyUpWebViewBinding>(R.layout.activity_healthy_up_web_view) {

    @Inject
    lateinit var webPreference: WebPreference

    private val viewModel by viewModels<HealthyUpWebViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.healthyUpWebView.setJavaScriptInterface(webPreference)
        binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
        //binding.healthyUpWebView.loadUrl("http://172.30.1.6:3000/test/custom")
        observeWebRequest()
    }

    private fun observeWebRequest() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WebAPIController.channelFlow.collect { jsonObject ->
                    when (jsonObject.get("functionName").asString) {
                        FunctionName.START_ACTIVITY -> startActivityFromWeb(jsonObject)
                    }
                }
            }
        }
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

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, HealthyUpWebViewActivity::class.java)
        }

        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, HealthyUpWebViewActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
