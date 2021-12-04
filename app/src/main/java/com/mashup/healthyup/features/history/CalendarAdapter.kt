package com.mashup.healthyup.features.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.healthyup.R
import com.mashup.healthyup.databinding.ItemCalendarBinding
import java.util.*

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var dateList: ArrayList<Int> = arrayListOf()
    private var items: List<ItemCalenderModel> = listOf()
    lateinit var defineCalendar: DefineCalendar
    lateinit var date: Calendar

    var firstDateIndex: Int = 0
    var lastDateIndex: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        )
    }

    override fun getItemCount(): Int = dateList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemCalendarBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        fun bind(item: ItemCalenderModel) {
            binding?.model = item

            binding?.tvCalenderDays?.let {
                if (position !in firstDateIndex..lastDateIndex) {
                    it.setTextColor(it.context.getColor(R.color.color_on_background_variant_3))
                }
                //TODO : 고민: bind 해줄 때 5개원 변경할지 bind 된 midel 에서 처리할지
            }

        }
    }

    fun setWriteDayList(exerciseList: List<ExerciseModel>) {
        val item = items.toMutableList()
        item.forEachIndexed { index, model ->
            if (index in firstDateIndex..lastDateIndex) {
                item[index] = ItemCalenderModel(
                    model.day, exerciseList.lastOrNull {
                        it.day == model.day
                    }?.status
                )
            }
        }
        items = item
        notifyDataSetChanged()
    }

    fun replaceAll(date: Calendar) {
        this.date = date
        defineCalendar = DefineCalendar(this.date.time)
        defineCalendar.initBaseCalendar()
        this.dateList = defineCalendar.dateList
        this.items = dateList.map {
            ItemCalenderModel(it, null)
        }
        firstDateIndex = defineCalendar.prevTail
        lastDateIndex = dateList.size - defineCalendar.nextHead - 1
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(date: String)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}
