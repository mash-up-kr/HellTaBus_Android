package com.mashup.healthyup.features.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.healthyup.R
import com.mashup.healthyup.databinding.ItemHistoryBinding
import com.mashup.healthyup.features.history.model.ExerciseModel
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


    fun replaceAll(exerciseList: List<ExerciseModel>) {
        val a = exerciseList.map {
            HistoryItem(
                it.day, dayOfWeek = it.dayOfWeek, part = it.part, subtitle = it.subtitle, status =
                it.status
            )
        }
        this.items = a
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
