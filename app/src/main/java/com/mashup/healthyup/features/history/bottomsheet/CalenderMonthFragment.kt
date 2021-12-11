package com.mashup.healthyup.features.history.bottomsheet

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseFragment
import com.mashup.healthyup.databinding.FragmentHistoryMonthBinding
import com.mashup.healthyup.features.history.HistoryActivity
import com.mashup.healthyup.features.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

interface OnBackPressedListener {
    fun onBackPressed()
}

@AndroidEntryPoint
class MonthFragment : BaseFragment<FragmentHistoryMonthBinding>(R.layout.fragment_history_month),
    OnBackPressedListener {

    private lateinit var date: Calendar
    private val calendarAdapter by lazy {
        CalenderMonthAdapter().apply {
            setOnItemClickListener(object : CalenderMonthAdapter.OnItemClickListener {
                override fun onItemClick(date: String) {
                    (activity as HistoryActivity).selectMonthCalender(date)
                    closeFragment()
                }
            })
        }
    }

    private val viewModel by viewModels<HistoryViewModel>()

    companion object {
        fun newInstance() = MonthFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        date = Calendar.getInstance()
        initRecyclerView()
        initView()

        binding.viewCalenderEmpty.setOnClickListener {
            closeFragment()
        }
//        viewModel.loadHistory()

    }

    private fun initView() {
        val datetime = SimpleDateFormat("yyyy.MM", Locale.KOREA).format(date.time)
        calendarAdapter.replaceAll(monthList(datetime))
    }

    private val startYear = 2021
    private fun monthList(datetime: String): List<String> {
        val list = mutableListOf<String>()
        val lastYear = datetime.split(".")[0].toInt()
        val lastMonth = datetime.split(".")[1].toInt()
        for (year in startYear..lastYear) {
            if (year == lastYear) {
                for (month in 1..lastMonth) {
                    list += "$year.$month"
                }
            } else {
                for (month in 1..12) {
                    list += "$year.$month"
                }
            }
        }
        return list.reversed()
    }

    private fun initRecyclerView() {
        binding.rvCalenderMonth.adapter = calendarAdapter
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@MonthFragment).commit()
            it.popBackStack()
        }
    }

    override fun onBackPressed() {
        closeFragment()
    }
}
