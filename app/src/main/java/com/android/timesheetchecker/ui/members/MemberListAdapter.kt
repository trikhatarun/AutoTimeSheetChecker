package com.android.timesheetchecker.ui.members

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.timesheetchecker.R
import com.android.timesheetchecker.data.database.model.UserLocal
import com.android.timesheetchecker.databinding.MemberListItemBinding
import java.util.*

class MemberListAdapter(private val listener: MemberListInteractionListener) :
    RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>() {

    private var data: List<UserLocal> = ArrayList()
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        return MemberViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.member_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<UserLocal>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class MemberViewHolder(private val binding: MemberListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserLocal) = with(binding) {
            memberName.text = item.name
            if (item.excluded) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorDisabledBackground
                    )
                )
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }

            binding.root.setOnClickListener {
                listener.onMemberClicked(item)
            }
            binding.executePendingBindings()
        }
    }

    interface MemberListInteractionListener {
        fun onMemberClicked(user: UserLocal)
    }
}