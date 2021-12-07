package com.mashup.healthyup.features.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.bridge.HealthyUpWebView
import com.mashup.healthyup.databinding.ActivityLauncherBinding
import com.mashup.healthyup.features.launcher.LauncherViewModel.Action.StartHome
import com.mashup.healthyup.features.launcher.LauncherViewModel.Action.StartLogin
import com.mashup.healthyup.features.login.LoginActivity
import com.mashup.healthyup.features.web.HealthyUpWebViewActivity
import com.mashup.healthyup.features.web.WebConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionFlow.collect { action ->
                    when (action) {
                        StartLogin -> startLoginActivity()
                        StartHome -> startHomeActivity()
                    }
                }
            }
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0);
        finish()
    }

    private fun startHomeActivity() {
        HealthyUpWebViewActivity.start(
            context = this,
            loadUrl = WebConstants.URL.HOME
        )
        overridePendingTransition(0, 0);
        finish()
    }
}
