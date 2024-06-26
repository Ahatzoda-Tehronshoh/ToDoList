package com.tehronshoh.todolist.presentation.ui.add_update.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tehronshoh.todolist.data.model.ToDoHistory

class ToDoHistoryDiffUtil: DiffUtil.ItemCallback<ToDoHistory>() {
    override fun areItemsTheSame(oldItem: ToDoHistory, newItem: ToDoHistory): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDoHistory, newItem: ToDoHistory): Boolean = oldItem == newItem
}