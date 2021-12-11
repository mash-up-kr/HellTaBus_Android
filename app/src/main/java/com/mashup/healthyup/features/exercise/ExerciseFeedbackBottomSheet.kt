/*
 * Created by Leo on 2021. 12. 11 ..
 */
package com.mashup.healthyup.features.exercise

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mashup.healthyup.Key
import com.mashup.healthyup.R
import com.mashup.healthyup.core.getForegroundBoldSpan
import com.mashup.healthyup.databinding.DialogExerciseFeedbackBinding
import com.mashup.healthyup.domain.entity.Exercise
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFeedbackBottomSheet private constructor() : BottomSheetDialogFragment() {

    interface ClickListener {
        fun onFeedbackSubmitClick()
    }

    private lateinit var binding: DialogExerciseFeedbackBinding
    private var listener: ClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? ClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ExerciseBottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogExerciseFeedbackBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val index = arguments?.getInt(Key.INDEX) ?: 0
        val exercise = arguments?.getSerializable(Key.EXERCISE) as? Exercise
        binding.feedbackTitle.text = resources.getStringArray(R.array.translated_stage_index)[index]
        binding.feedbackSubscribe.text = getForegroundBoldSpan(
            requireContext(),
            colorRes = R.color.color_primary,
            totalString = getString(
                R.string.exercise_feedback_subscribe_format,
                exercise?.name.toString()
            ),
            coloredString = exercise?.name.toString(),
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSubmit.setOnClickListener {
            listener?.onFeedbackSubmitClick()
            dismiss()
        }
    }

    companion object {

        private const val TAG = "EXERCISE_FEEDBACK"

        fun newInstance(exercise: Exercise, index: Int): ExerciseFeedbackBottomSheet {
            return ExerciseFeedbackBottomSheet().apply {
                arguments = bundleOf(
                    Key.EXERCISE to exercise,
                    Key.INDEX to index
                )
            }
        }

        fun show(fm: FragmentManager, exercise: Exercise, index: Int) {
            val dialog = newInstance(exercise, index)
            fm.executePendingTransactions()
            if (fm.findFragmentByTag(TAG) == null) {
                dialog.show(fm, TAG)
            }
        }
    }
}
