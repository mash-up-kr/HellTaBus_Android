package com.mashup.healthyup.features.history

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.mashup.healthyup.R
import com.mashup.healthyup.base.BaseActivity
import com.mashup.healthyup.databinding.ActivityHistoryBinding
import com.mashup.healthyup.features.history.adapter.CalendarAdapter
import com.mashup.healthyup.features.history.adapter.HistoryAdapter
import com.mashup.healthyup.features.history.bottomsheet.MonthFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
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
    private val historyAdapter by lazy {
        HistoryAdapter().apply {
            setOnItemClickListener(object : HistoryAdapter.OnItemClickListener {
                override fun onItemClick(date: String) {
                }
            })
        }
    }


    override fun initViews() {
        binding.viewModel = viewModel
        initRecyclerView()
        initCalender()
        setStatusBarColor()
    }

    private fun setStatusBarColor() {
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor =
                ContextCompat.getColor(this@HistoryActivity, R.color.color_background)
        }
    }

    override fun initObserves() {
        super.initObserves()
        viewModel.exerciseHistoryList.observe(this) {
            historyAdapter.replaceAll(it)
            calendarAdapter.setWriteDayList(it)
        }

        viewModel.onClickMonth.observe(this) {
            addSelectedCalenderMonthFragment()
        }
    }

    private fun initRecyclerView() {
        binding.rvCalendar.adapter = calendarAdapter
        binding.rvHistory.adapter = historyAdapter
    }

    private fun initCalender() {
        date = Calendar.getInstance()
        val datetime = SimpleDateFormat("yyyy.MM", Locale.KOREA).format(date.time)

        binding.tvMonth.text = datetime
        calendarAdapter.replaceAll(date)

        viewModel.loadHistory(getDate())
    }

    private fun getDate(): List<String> {
        val month = SimpleDateFormat("yyyy-MM", Locale.KOREA).format(date.time)
        return listOf(
            "$month-1",
            "$month-${date.getActualMaximum(Calendar.DAY_OF_MONTH)}"
        )
    }

    fun selectMonthCalender(_datetime: String) {
        val datetime = _datetime.split(".")
        date.set(datetime[0].toInt(), (datetime[1].toInt() - 1), 1)
        binding.tvMonth.text = _datetime
        calendarAdapter.replaceAll(date)
        viewModel.loadHistory(getDate())
    }

    private fun addSelectedCalenderMonthFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_calender_month, MonthFragment.newInstance())
        }.commit()
    }


    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, HistoryActivity::class.java)
        }

        fun start(context: Context, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, HistoryActivity::class.java).apply(action)
            context.startActivity(intent)
        }
    }
}
