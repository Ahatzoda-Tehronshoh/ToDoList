package com.tehronshoh.todolist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tehronshoh.todolist.data.ToDo
import com.tehronshoh.todolist.databinding.TodoItemBinding
import com.tehronshoh.todolist.util.ToDoDiffUtil


class ToDoRecyclerViewAdapter: ListAdapter<ToDo, ToDoRecyclerViewAdapter.ToDoViewHolder>(
    ToDoDiffUtil()
) {

    var onItemClickListener: ((ToDo) -> (Unit))? = null

    inner class ToDoViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: ToDo) {
            binding.id.text = todo.id.toString()
            binding.title.text = todo.title
            binding.description.text = todo.description
        }

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition >= 0)
                    onItemClickListener?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder = ToDoViewHolder(
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