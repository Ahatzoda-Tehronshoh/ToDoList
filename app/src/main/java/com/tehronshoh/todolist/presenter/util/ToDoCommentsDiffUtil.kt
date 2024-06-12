package com.tehronshoh.todolist.presenter.util

import androidx.recyclerview.widget.DiffUtil
import com.tehronshoh.todolist.data.model.ToDoComments

class ToDoCommentsDiffUtil: DiffUtil.ItemCallback<ToDoComments>() {
    override fun areItemsTheSame(oldItem: ToDoComments, newItem: ToDoComments): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ToDoComments, newItem: ToDoComments): Boolean = oldItem == newItem
}