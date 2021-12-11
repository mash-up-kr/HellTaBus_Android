package com.mashup.healthyup.features.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.core.showToast
import com.mashup.healthyup.databinding.ActivityExerciseSummaryBinding
import com.mashup.healthyup.features.summary.ExerciseSummaryViewModel.Action.OnCloseClicked
import com.mashup.healthyup.features.summary.ExerciseSummaryViewModel.Action.OnImageSaveClicked
import com.mashup.healthyup.features.summary.ExerciseSummaryViewModel.Action.OnShareClicked
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ExerciseSummaryActivity :
    BaseActivity<ActivityExerciseSummaryBinding>(R.layout.activity_exercise_summary) {

    private val viewModel by viewModels<ExerciseSummaryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor =
                ContextCompat.getColor(this@ExerciseSummaryActivity, R.color.color_primary_variant3)
        }
    }

    override fun initObserves() {
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actionFlow.collect { action ->
                    when (action) {
                        OnCloseClicked -> finish()
                        OnImageSaveClicked -> {
                            showToast(message = "이미지를 저장했어요.")
                            finish()
                        }
                        OnShareClicked -> {
                            showToast(message = "공유기능은 곧 오픈될 예정이에요.")
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, ExerciseSummaryActivity::class.java)
        }

        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, ExerciseSummaryActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
