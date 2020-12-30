package com.android.timesheetchecker.ui.membersdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.timesheetchecker.R
import com.android.timesheetchecker.data.network.models.Entry
import com.android.timesheetchecker.databinding.EntriesListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class EntriesAdapter : RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>() {

    private var data: List<Entry> = ArrayList()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntriesViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        return EntriesViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.entries_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: EntriesViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<Entry>) {
        this.data = data
        notifyDataSetChanged()
    }

    class EntriesViewHolder(private val binding: EntriesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Entry) = with(binding) {
            val dateInstance = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(item.date)

            if (dateInstance != null) {
                dateMonth.text =
                    SimpleDateFormat("EEE dd-MMM", Locale.getDefault()).format(dateInstance)
            }

            hours.text = String.format("%.2f hrs", item.hours)
        }
    }
}