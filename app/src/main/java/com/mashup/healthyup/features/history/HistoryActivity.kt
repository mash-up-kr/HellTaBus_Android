package com.mashup.healthyup.features.history

import android.os.Bundle
import androidx.activity.viewModels
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : BaseActivity<ActivityHistoryBinding>(R.layout.activity_history) {
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var date: Calendar

    private val calendarAdapter by lazy {
        CalendarAdapter().apply {
            setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
                override fun onItemClick(date: String) {
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.exerciseList.observe(this) {
            calendarAdapter.setWriteDayList(it)
        }
    }

    override fun initViews() {
        date = Calendar.getInstance()
        val datetime = SimpleDateFormat("yyyy.MM", Locale.KOREA).format(date.time)
        binding.tvMonth.text = datetime
        calendarAdapter.replaceAll(date)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.calendarRecyclerView.adapter = calendarAdapter
    }
}