package com.tehronshoh.todolist.presentation.ui.add_update.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.databinding.CommentsAssigneeItemBinding
import com.tehronshoh.todolist.databinding.CommentsCreatorItemBinding

class ToDoCommentsRecyclerViewAdapter(private val toDoCreatorEmail: String) :
    ListAdapter<ToDoComments, ToDoCommentsRecyclerViewAdapter.ToDoCommentsViewHolder>(
        ToDoCommentsDiffUtil()
    ) {
    abstract class ToDoCommentsViewHolder(private val view: View) : ViewHolder(view) {
        abstract fun bind(toDoComments: ToDoComments)
    }

    inner class ToDoCommentsCreatorViewHolder(private val binding: CommentsCreatorItemBinding) :
        ToDoCommentsViewHolder(binding.root) {

        override fun bind(toDoComments: ToDoComments) {
            binding.creatorComment.text = toDoComments.comment
            binding.date.text = toDoComments.date
        }
    }

    inner class ToDoCommentsAssigneeViewHolder(private val binding: CommentsAssigneeItemBinding) :
        ToDoCommentsViewHolder(binding.root) {

        override fun bind(toDoComments: ToDoComments) {
            binding.assigneeComment.text = toDoComments.comment
            binding.date.text = toDoComments.date
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).from == toDoCreatorEmail) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoCommentsViewHolder =
        if (viewType == 1)
            ToDoCommentsCreatorViewHolder(
                CommentsCreatorItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            ToDoCommentsAssigneeViewHolder(
                CommentsAssigneeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    override fun onBindViewHolder(
        holder: ToDoCommentsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}