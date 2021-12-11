package com.mashup.healthyup.features.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.core.visibleOrGone
import com.mashup.healthyup.databinding.ActivityLoginBinding
import com.mashup.healthyup.features.login.LoginViewModel.Action.*
import com.mashup.healthyup.features.web.HealthyUpWebViewActivity
import com.mashup.healthyup.features.web.WebConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.doOnGoogleLoginSuccess(account.idToken)
                } catch (e: ApiException) {
                    Log.e(TAG, "Google sign in failed", e)
                }
            }
        }

    private val googleSignInClient by lazy<GoogleSignInClient> {
        GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.channelFlow.collect { action ->
                    when (action) {
                        ClickLogin -> {
                            setLoadingIndicator(true)
                            val intent = googleSignInClient.signInIntent
                            resultLauncher.launch(intent)
                        }
                        StartWebViewHome -> {
                            setLoadingIndicator(true)
                            startWebViewActivity(WebConstants.URL.HOME)
                            overridePendingTransition(0, 0);
                            finish()
                        }
                        StartWebViewSurvey -> {
                            setLoadingIndicator(true)
                            startWebViewActivity(WebConstants.URL.SURVEY)
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun startWebViewActivity(url: String) {
        HealthyUpWebViewActivity.start(
            context = this,
            loadUrl = url
        )
    }

    private fun setLoadingIndicator(visible: Boolean) {
        binding.indicator.cvLoading.visibleOrGone(visible)

        if (visible) {
            Glide.with(this)
                .load(R.raw.img_loading)
                .centerCrop()
                .into(binding.indicator.ivLoading)
        }
    }
}
