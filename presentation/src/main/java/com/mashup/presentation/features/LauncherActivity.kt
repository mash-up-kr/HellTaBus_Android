/*
 * Created by Leo on 2021. 10. 02 ..
 */
package com.mashup.presentation.features

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            binding.graphView.setCueList()
        }

    }
}
