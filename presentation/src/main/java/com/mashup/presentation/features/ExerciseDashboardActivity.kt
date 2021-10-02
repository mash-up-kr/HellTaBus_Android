/*
 * Created by Leo on 2021. 10. 03 ..
 */
package com.mashup.presentation.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.presentation.R
import com.mashup.presentation.base.BaseActivity
import com.mashup.presentation.databinding.ActivityExerciseDashboardBinding
import com.mashup.presentation.databinding.DialogExerciseFeedbackBinding
import com.mashup.presentation.databinding.FragmentExcerciseSummaryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseDashboardActivity : BaseActivity<ActivityExerciseDashboardBinding>(R.layout.activity_exercise_dashboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            binding.circleGraphView.setCueList()
        }

        Glide.with(this)
            .asGif()
            .load(R.raw.gif_exercise_1)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.ivExercisePose)

        binding.tvExerciseDone.setOnClickListener {

            val bottomSheet = ExcerciseFeedbackDialogFragment()
            bottomSheet.show(supportFragmentManager, "bottomSheet")
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ExerciseDashboardActivity::class.java)
            context.startActivity(intent)
        }
    }
}

class ExcerciseFeedbackDialogFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinder: DialogExerciseFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewBinder = DialogExerciseFeedbackBinding.inflate(inflater, container, false)
        return viewBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinder.buttonSubmit.setOnClickListener {
            dismiss()
        }
    }
}
