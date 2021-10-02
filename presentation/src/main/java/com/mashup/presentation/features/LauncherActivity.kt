/*
 * Created by Leo on 2021. 10. 02 ..
 */
package com.mashup.presentation.features

import android.os.Bundle
import androidx.activity.viewModels
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityLauncherBinding
import com.mashup.presentation.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>(R.layout.activity_launcher) {

    private val viewModel by viewModels<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginActivity.start(this)
    }
}
