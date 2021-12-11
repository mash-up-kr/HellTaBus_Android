/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.mashup.healthyup.Key
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.core.AbstractAnimatorListener
import com.mashup.healthyup.core.dp10
import com.mashup.healthyup.core.dp5
import com.mashup.healthyup.core.visibleOrGone
import com.mashup.healthyup.databinding.ActivityExerciseDashboardBinding
import com.mashup.healthyup.databinding.LayoutDotBinding
import com.mashup.healthyup.databinding.LayoutProgressBinding
import com.mashup.healthyup.domain.entity.Exercise
import com.mashup.healthyup.features.exercise.ExerciseDashboardViewModel.Action.OnExerciseDoneClicked
import com.mashup.healthyup.features.summary.ExerciseSummaryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseDashboardActivity :
    BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard),
    ExerciseFeedbackBottomSheet.ClickListener {

    private val viewModel by viewModels<ExerciseDashboardViewModel>()
    private var gifDrawable: GifDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        binding.viewModel = viewModel

        binding.ivAudioWave.addAnimatorListener(object : AbstractAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                viewModel.onAudioEnd()
            }
        })

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                val exerciseList = state.exerciseList
                val index = state.index
                val stage = state.stage

                if (state.isInitialLoad) {
                    // load when first state emitted
                    addProgressViews(exerciseList)
                }

                if (state.index != state.exerciseList.count() - 1) {
                    binding.layoutNext.visibleOrGone(true)
                    binding.tvNextExercise.text = exerciseList[index + 1].name
                }

                binding.tvExerciseIndex.text =
                    resources.getStringArray(R.array.translated_stage_index)[index]
                binding.tvExerciseTitle.text = stage.name
                binding.tvExerciseWeightAndCount.text = getString(
                    R.string.exercise_dashboard_set_format,
                    stage.startWeight,
                    stage.baseCount
                )

                binding.ivAudioWave.visibleOrGone(state.onAudioPlaying)
                binding.ivExerciseGif.visibleOrGone(!state.onAudioPlaying)
                if (!state.onAudioPlaying) {
                    loadExerciseGif(state.stage.imageLink)
                    setExerciseGraph()
                    binding.tvConfirmStage.setOnClickListener {
                        viewModel.onExerciseDoneClick()
                    }
                }

                binding.tvConfirmStage.text = if (state.onAudioPlaying) {
                    getString(R.string.exercise_dashboard_skip_audio)
                } else {
                    getString(R.string.exercise_dashboard_confirm_current_stage, index + 1)
                }

                if (state.isPaused) {
                    if (gifDrawable?.isRunning == true) {
                        gifDrawable?.stop()
                    }
                } else {
                    if (gifDrawable?.isRunning == false) {
                        gifDrawable?.startFromFirstFrame()
                    }
                }

                val ivPauseRes = if (!state.isPaused) R.drawable.ic_pause else R.drawable.ic_refresh
                binding.ivPause.setImageResource(ivPauseRes)
                binding.tvDate.text = state.todayDate
            }
        }

        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.actionFlow.collect { action ->
                    when (action) {
                        is OnExerciseDoneClicked -> {
                            ExerciseFeedbackBottomSheet.show(
                                supportFragmentManager,
                                action.exercise,
                                action.index
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // TODO : `운동을 그만두시겠어요?` 다이얼로그가 노출되어야함.
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initViews() {
        super.initViews()

        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(this@ExerciseDashboardActivity, R.color.white)
        }
    }

    override fun onFeedbackSubmitClick() {
        ExerciseSummaryActivity.start(this)
        finish()
    }

    private fun setExerciseGraph() {
        lifecycleScope.launch {
            binding.graphView.setCueList()
        }
    }

    private fun loadExerciseGif(url: String) {
        Glide.with(this)
            .asGif()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(200, 200)
            .into(object : CustomViewTarget<ImageView, GifDrawable>(binding.ivExerciseGif) {
                override fun onLoadFailed(errorDrawable: Drawable?) {
                    /* explicitly empty */
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    /* explicitly empty */
                }

                override fun onResourceReady(
                    resource: GifDrawable,
                    transition: Transition<in GifDrawable>?
                ) {
                    gifDrawable = resource
                    resource.start()
                    binding.ivExerciseGif.setImageDrawable(resource)
                }
            })
    }

    private fun addProgressViews(exerciseList: List<Exercise>) {
        exerciseList.forEachIndexed { index, _ ->
            // - * - * - * - 형태, 마지막이면, dot 을 붙이지 않음!
            val progressBar = LayoutProgressBinding.inflate(layoutInflater)
            val dot = LayoutDotBinding.inflate(layoutInflater).root

            binding.layoutProgress.addView(progressBar.root, getWeightedLayoutParams(1f))
            // 임의로 첫번째 progress 만 진행되도록 처리.
            if (index == 0) {
                lifecycleScope.launch {
                    fun getCurrentProgress(): Int {
                        return progressBar.progressBar.progress
                    }

                    while (getCurrentProgress() != 100) {
                        progressBar.progressBar.progress = getCurrentProgress() + 5
                        delay(1000L)
                    }
                }
            }

            if (index != exerciseList.size - 1) {
                binding.layoutProgress.addView(
                    dot,
                    LinearLayout.LayoutParams(dp10, dp10).apply {
                        marginStart = dp5
                        marginEnd = dp5
                    }
                )
            }

        }
    }

    private fun getWeightedLayoutParams(weight: Float): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.weight = weight
        return params
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, ExerciseDashboardActivity::class.java)
        }

        fun start(
            context: Context,
            exerciseList: ArrayList<Exercise>,
            action: Intent.() -> Unit = {}
        ) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java).apply(action)
            intent.putExtra(Key.EXERCISE_LIST, exerciseList)
            context.startActivity(intent)
        }
    }
}
