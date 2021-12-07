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
import com.mashup.healthyup.features.history.HistoryActivity
import com.mashup.healthyup.features.setting.SettingActivity
import com.mashup.healthyup.features.web.WebConstants.Target
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
        binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/exercise-routine")
        observeWebRequest()
    }

    override fun initListeners() {
        super.initListeners()
        binding.btnLoad.setOnClickListener {
            binding.healthyUpWebView.loadUrl(binding.etUrl.text.toString())
        }
    }

    private fun observeWebRequest() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WebAPIController.channelFlow.collect { jsonObject ->
                    when (jsonObject.get(WebConstants.FUNCTION_NAME).asString) {
                        FunctionName.START_ACTIVITY -> startActivityFromWeb(jsonObject)
                        FunctionName.SET_BACK_BUTTON_RECEIVE -> {
                            binding.viewModel?.backButtonReceiveTarget = jsonObject.get("target").asString
                        }
                    }
                }
            }
        }
    }

    private fun startActivityFromWeb(options: JsonObject) {
        when (options.get("target").asString) {
            Target.SETTING -> {
                SettingActivity.start(this) {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            }
            Target.HISTORY -> {
                HistoryActivity.start(this) {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            }
            Target.HOME -> {
                // TODO: 웹에서 내려온, home url, access token, 만료시간을 파싱해서 처리
                val loadUrl = options.get("loadUrl").asString
                start(this, loadUrl) {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
            }
            Target.EXERCISE -> {
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
        when (binding.viewModel?.backButtonReceiveTarget) {
            Target.ANDROID -> {
                if (binding.healthyUpWebView.canGoBack()) {
                    binding.healthyUpWebView.goBack()
                } else {
                    super.onBackPressed()
                }
            }
            Target.WEB -> {
                WebAPIController.sendNativeEvent(FunctionName.ON_BACK_BUTTON_PRESSED, null)
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

        fun start(context: Context, loadUrl: String, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, HealthyUpWebViewActivity::class.java).apply(action)
            intent.putExtra(Key.LOAD_URL, loadUrl)
            context.startActivity(intent)
        }
    }
}
