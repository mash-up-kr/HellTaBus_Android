package com.mashup.healthyup.features

import android.os.Bundle
import androidx.activity.viewModels
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.JavaScriptInterface
import com.mashup.healthyup.databinding.ActivityLauncherBinding
import com.mashup.healthyup.features.exercise.ExerciseDashboardFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private var javaScriptInterface: JavaScriptInterface? = null
    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            javaScriptInterface = binding.healthyUpWebView.javaScriptInterface
//            binding.healthyUpWebView.loadUrl("https://helltabus-dev.netlify.app/survey")
//        }

        val fragment = ExerciseDashboardFragment.newInstance(0L)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, "dashboard")
            .commitNow()
    }
}
