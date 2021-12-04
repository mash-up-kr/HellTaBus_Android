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
import com.mashup.healthyup.Key
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.WebAPIController
import com.mashup.healthyup.bridge.WebAPIController.FunctionName
import com.mashup.healthyup.bridge.WebPreference
import com.mashup.healthyup.core.Empty
import com.mashup.healthyup.databinding.ActivityHealthyUpWebViewBinding
import com.mashup.healthyup.features.exercise.ExerciseDashboardActivity
import com.mashup.healthyup.features.setting.SettingActivity
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

    private val loadUrl by lazy {
        intent?.getStringExtra(Key.LOAD_URL) ?: String.Empty
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.healthyUpWebView.setJavaScriptInterface(webPreference)
        binding.healthyUpWebView.loadUrl(loadUrl)
        //binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
        observeWebRequest()
    }

    private fun observeWebRequest() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WebAPIController.channelFlow.collect { jsonObject ->
                    when (jsonObject.get(WebConstants.FUNCTION_NAME).asString) {
                        FunctionName.START_ACTIVITY -> startActivityFromWeb(jsonObject)
                    }
                }
            }
        }
    }

    private fun startActivityFromWeb(options: JsonObject) {
        when (options.get("target").asString) {
            WebConstants.Target.SETTING -> {
                SettingActivity.start(this) {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            }
            WebConstants.Target.HISTORY -> {
                // TODO: 히스토리 (달력 화면) 으로 이동
            }
            WebConstants.Target.HOME -> {
                // TODO: 웹에서 내려온, home url, access token, 만료시간을 파싱해서 처리
                val loadUrl = options.get("loadUrl").asString
                start(this, loadUrl) {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
            }
            WebConstants.Target.EXERCISE -> {
                // TODO: 웹으로부터 받은 데이터 저장 추가 필요
                val exerciseArray = options.get("exerciseList").asJsonPrimitive
                Log.d("HealthyUpWebViewActivity", "exerciseArray: $exerciseArray")
                ExerciseDashboardActivity.start(this) {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.healthyUpWebView.canGoBack()) {
            binding.healthyUpWebView.goBack()
        } else {
            super.onBackPressed()
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

        fun start(context: Context, loadUrl: String, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, HealthyUpWebViewActivity::class.java).apply(action)
            intent.putExtra(Key.LOAD_URL, loadUrl)
            context.startActivity(intent)
        }
    }
}
