package com.tehronshoh.todolist.ui.util

import androidx.recyclerview.widget.DiffUtil
import com.tehronshoh.todolist.data.model.ToDo

class ToDoDiffUtil: DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean = oldItem == newItem
}