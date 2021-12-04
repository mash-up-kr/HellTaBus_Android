package com.mashup.healthyup.features.history.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.healthyup.R
import com.mashup.healthyup.databinding.ItemBottomSheetMonthBinding

class CalenderMonthAdapter : RecyclerView.Adapter<CalenderMonthAdapter.CategoryViewHolder>() {

    private val items = mutableListOf<String>()
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(parent).apply {
            itemView.setOnClickListener {
                mListener?.onItemClick(items[this.layoutPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun replaceAll(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class CategoryViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bottom_sheet_month, parent, false)
    ) {
        private val binding: ItemBottomSheetMonthBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: String) {
            binding?.month = item
        }
    }
}
