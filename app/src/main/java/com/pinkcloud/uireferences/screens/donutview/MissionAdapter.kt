package com.pinkcloud.uireferences.screens.donutview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkcloud.uireferences.database.Mission
import com.pinkcloud.uireferences.databinding.ListItemMissionBinding

class MissionAdapter(val onDelete: (Int) -> Unit): ListAdapter<MissionItem, MissionAdapter.ViewHolder>(MissionItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: ListItemMissionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(missionItem: MissionItem) {
            binding.missionItem = missionItem
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup):ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemMissionBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun deleteItem(position: Int) {
        val mission = getItem(position)
        onDelete(mission.missionId)
    }

    class MissionItemDiffCallback: DiffUtil.ItemCallback<MissionItem>() {
        override fun areItemsTheSame(oldItem: MissionItem, newItem: MissionItem): Boolean {
            return oldItem.missionId == newItem.missionId
        }

        override fun areContentsTheSame(oldItem: MissionItem, newItem: MissionItem): Boolean {
            return oldItem == newItem
        }

    }
}