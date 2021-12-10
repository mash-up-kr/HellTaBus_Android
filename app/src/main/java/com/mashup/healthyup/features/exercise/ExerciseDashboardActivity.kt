/*
 * Created by Leo on 2021. 11. 13 ..
 */
package com.mashup.healthyup.features.exercise

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mashup.healthyup.Key
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.core.AbstractAnimatorListener
import com.mashup.healthyup.core.visibleOrGone
import com.mashup.healthyup.databinding.ActivityExerciseDashboardBinding
import com.mashup.healthyup.databinding.LayoutProgressBinding
import com.mashup.healthyup.domain.entity.Exercise
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ExerciseDashboardActivity :
    BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    private val viewModel by viewModels<ExerciseDashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                if (state.index != state.exerciseList.count() - 1) {
                    binding.layoutNext.visibleOrGone(true)
                    binding.tvNextExercise.text = exerciseList[index + 1].name
                }

                binding.tvExerciseIndex.text = resources.getStringArray(R.array.translated_stage_index)[index]
                binding.tvExerciseTitle.text = stage.name
                binding.tvExerciseWeightAndCount.text = getString(R.string.exercise_dashboard_set_format, stage.startWeight, stage.baseCount)

                binding.ivAudioWave.visibleOrGone(state.onAudioPlaying)
                binding.ivExerciseGif.visibleOrGone(!state.onAudioPlaying)
                if (!state.onAudioPlaying) {
                    loadExerciseGif(state.stage.imageLink)
                }

                binding.tvConfirmStage.text = if (state.onAudioPlaying) {
                    getString(R.string.exercise_dashboard_skip_audio)
                } else {
                    getString(R.string.exercise_dashboard_confirm_current_stage, index + 1)
                }

                val ivPauseRes = if (state.isPaused) R.drawable.ic_pause else R.drawable.ic_refresh
                binding.ivPause.setImageResource(ivPauseRes)
                binding.tvDate.text = state.todayDate
            }

            // TODO: 홈(웹뷰) 에서 넘겨받은 운동정보 리스트를 가지고, Graph 설정
            //binding.graphView.setCueList()
        }
    }

    override fun initViews() {
        // TODO : TODO: 홈(웹뷰) 에서 넘겨받은 운동정보 리스트를 가지고, 필요한 만큼 layout_progress 동적으로 addView
        addProgressViews()
    }

    private fun loadExerciseGif(url: String) {
        Glide.with(this)
            .asGif()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(200, 200)
            .into(binding.ivExerciseGif)
    }

    private fun addProgressViews() {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.weight = 1f

        listOf(0, 1, 2).forEach { _ ->
            val progressView = LayoutProgressBinding.inflate(layoutInflater).root
            binding.layoutProgress.addView(progressView, params)
        }
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
