package com.mashup.healthyup.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.JavaScriptInterface
import com.mashup.healthyup.databinding.ActivityLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private var javaScriptInterface: JavaScriptInterface? = null
    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
//            binding.graphView.setCueList()
            javaScriptInterface = binding.healthyUpWebView.javaScriptInterface
//            binding.healthyUpWebView.loadUrl("http://210.97.97.157:3000/test/custom")
            binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
        }
    }
}
