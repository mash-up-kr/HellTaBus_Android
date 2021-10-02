package com.mashup.presentation.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityWebviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            binding.buttonStartExcercise.setOnClickListener {
                start(this@WebViewActivity)
            }
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java)
            context.startActivity(intent)
        }
    }
}