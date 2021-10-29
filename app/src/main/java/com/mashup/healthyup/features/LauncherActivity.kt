package com.mashup.healthyup.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.healthyup.R
import com.mashup.healthyup.databinding.ActivityLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity :
    com.mashup.healthyup.base.BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            binding.graphView.setCueList()
        }

    }
}
