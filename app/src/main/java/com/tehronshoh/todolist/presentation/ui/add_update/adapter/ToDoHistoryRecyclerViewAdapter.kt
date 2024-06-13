package com.tehronshoh.todolist.presentation.ui.add_update.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.data.model.ToDoStatus
import com.tehronshoh.todolist.databinding.HistoryItemBinding

class ToDoHistoryRecyclerViewAdapter :
    ListAdapter<ToDoHistory, ToDoHistoryRecyclerViewAdapter.ToDoHistoryViewHolder>(
        ToDoHistoryDiffUtil()
    ) {
    inner class ToDoHistoryViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todoHistory: ToDoHistory) {
            binding.updateDate.text = todoHistory.changedAt

            setStatus(todoHistory)
        }

        private fun setStatus(todoHistory: ToDoHistory) {
            when (ToDoStatus.valueOf(todoHistory.newStatus)) {
                ToDoStatus.WAITING -> {
                    binding.statusText.text = binding.root.context.getString(R.string.waiting)
                    val statusColor =
                        binding.root.context.getColorStateList(R.color.waiting_status_color)
                    binding.newStatusCard.setCardBackgroundColor(statusColor)
                    binding.updateDate.setTextColor(statusColor)
                }

                ToDoStatus.IN_PROCESS -> {
                    binding.statusText.text = binding.root.context.getString(R.string.in_process)
                    val statusColor =
                        binding.root.context.getColorStateList(R.color.in_process_status_color)
                    binding.newStatusCard.setCardBackgroundColor(statusColor)
                    binding.updateDate.setTextColor(statusColor)
                }

                ToDoStatus.DONE -> {
                    binding.statusText.text = binding.root.context.getString(R.string.done)
                    val statusColor =
                        binding.root.context.getColorStateList(R.color.done_status_color)
                    binding.newStatusCard.setCardBackgroundColor(statusColor)
                    binding.updateDate.setTextColor(statusColor)
                }

                ToDoStatus.CLOSED -> {
                    binding.statusText.text = binding.root.context.getString(R.string.closed)
                    val statusColor =
                        binding.root.context.getColorStateList(R.color.closed_status_color)
                    binding.newStatusCard.setCardBackgroundColor(statusColor)
                    binding.updateDate.setTextColor(statusColor)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHistoryViewHolder =
        ToDoHistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ToDoHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}