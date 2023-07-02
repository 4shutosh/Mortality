package com.planner.mortality.ui.routine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.planner.mortality.data.entities.RoutineEntity
import com.planner.mortality.databinding.ListItemRoutineBinding

class RoutineAdapter :
    ListAdapter<RoutineAdapter.RoutineViewState, RoutineAdapter.RoutineViewHolder>(
        RoutineListDiffCallback) {

    data class RoutineViewState(
        val id: Long,
        val title: String,
        val description: String,
        val endTimeStamp: Long,
    )

    inner class RoutineViewHolder(val binding: ListItemRoutineBinding) :
        RecyclerView.ViewHolder(binding.root)

    object RoutineListDiffCallback : DiffUtil.ItemCallback<RoutineViewState>() {
        override fun areItemsTheSame(
            oldItem: RoutineViewState,
            newItem: RoutineViewState,
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: RoutineViewState,
            newItem: RoutineViewState,
        ) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RoutineViewHolder(
        ListItemRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description
        }
    }
}

fun RoutineEntity.toViewState() = RoutineAdapter.RoutineViewState(
    id = id,
    title = title,
    description = description,
    endTimeStamp = endTimeStamp
)
