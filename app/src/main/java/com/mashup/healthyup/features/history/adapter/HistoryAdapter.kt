package com.mashup.healthyup.features.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.healthyup.R
import com.mashup.healthyup.databinding.ItemHistoryBinding
import com.mashup.healthyup.features.history.model.ExerciseHistoryModel
import com.mashup.healthyup.features.history.model.HistoryItem

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryAdapter>() {

    private var items: List<HistoryItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter {
        return HistoryAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HistoryAdapter, position: Int) {
        holder.bind(items[position])
    }

    inner class HistoryAdapter(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemHistoryBinding? =
            androidx.databinding.DataBindingUtil.bind(itemView)

        fun bind(item: HistoryItem) {
            binding?.model = item
        }
    }


    fun replaceAll(exerciseHistoryList: List<ExerciseHistoryModel>) {
        val subtitleWeight = exerciseHistoryList.map {
            var a = 0
            it.status.forEach { item ->
                a += item.count * item.set * item.weight
            }
            HistoryItem(
                it.getDay(),
                dayOfWeek = it.getDayOfWeek() + "요일",
                part = it.getPart(),
                subtitle = it.getSubtitle(a),
                status =
                it.status
            )
        }
        this.items = subtitleWeight
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
