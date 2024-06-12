package com.tehronshoh.todolist.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.ToDoStatus
import com.tehronshoh.todolist.databinding.TodoItemBinding
import com.tehronshoh.todolist.ui.util.ToDoDiffUtil


class ToDoRecyclerViewAdapter : ListAdapter<ToDo, ToDoRecyclerViewAdapter.ToDoViewHolder>(
    ToDoDiffUtil()
) {

    var onItemClickListener: ((ToDo) -> (Unit))? = null
    var onDeleteListener: ((ToDo) -> (Unit))? = null

    inner class ToDoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: ToDo) {
            binding.title.text = todo.title
            binding.description.text = todo.description
            binding.creator.text = todo.creatorEmail
            binding.assignee.text = todo.assigneeEmail
            binding.due.text = todo.dueDate

            setStatus(todo)
        }

        private fun setStatus(todo: ToDo) {
            when (ToDoStatus.valueOf(todo.status)) {
                ToDoStatus.WAITING -> {
                    binding.statusText.text = binding.root.context.getString(R.string.waiting)
                    binding.statusCard.setCardBackgroundColor(
                        binding.root.context.getColorStateList(
                            R.color.waiting_status_color
                        )
                    )
                }

                ToDoStatus.IN_PROCESS -> {
                    binding.statusText.text = binding.root.context.getString(R.string.in_process)
                    binding.statusCard.setCardBackgroundColor(
                        binding.root.context.getColorStateList(
                            R.color.in_process_status_color
                        )
                    )
                }

                ToDoStatus.DONE -> {
                    binding.statusText.text = binding.root.context.getString(R.string.done)
                    binding.statusCard.setCardBackgroundColor(
                        binding.root.context.getColorStateList(
                            R.color.done_status_color
                        )
                    )
                }

                ToDoStatus.CLOSED -> {
                    binding.statusText.text = binding.root.context.getString(R.string.closed)
                    binding.statusCard.setCardBackgroundColor(
                        binding.root.context.getColorStateList(
                            R.color.closed_status_color
                        )
                    )
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition >= 0)
                    onItemClickListener?.invoke(getItem(bindingAdapterPosition))
            }
            binding.deleteButton.setOnClickListener {
                if (bindingAdapterPosition >= 0)
                    onDeleteListener?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder =
        ToDoViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}