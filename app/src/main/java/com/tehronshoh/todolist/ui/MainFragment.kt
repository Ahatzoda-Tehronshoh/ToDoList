package com.tehronshoh.todolist.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.databinding.FragmentMainBinding
import com.tehronshoh.todolist.ui.util.MainViewModelFactory

/**
 * A fragment representing a list of To_Do.
 */
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupRecyclerView()
        addButtonClick()
    }

    private fun initViewModel() {
        val mainViewModelFactory =
            MainViewModelFactory(ToDoDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        )[MainViewModel::class.java]
    }

    private fun addButtonClick() {
        binding.addTaskBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }

    private fun setupRecyclerView() {
        val toDoAdapter = ToDoRecyclerViewAdapter()
        with(binding.rvTodo) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = toDoAdapter.also {
                it.onItemClickListener = { toDo ->
                    val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(toDo)
                    findNavController().navigate(action)
                }
                it.onDeleteListener = { toDo ->
                    showDeleteConfirmationDialog {
                        mainViewModel.deleteToDo(toDo)
                        Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        mainViewModel.toDos.observe(viewLifecycleOwner) { toDoList ->
            if (toDoList.isEmpty()) {
                binding.noDataImage.visibility = View.VISIBLE
                binding.noDataText.visibility = View.VISIBLE
            } else {
                binding.noDataImage.visibility = View.GONE
                binding.noDataText.visibility = View.GONE
                toDoAdapter.submitList(toDoList)
            }
        }
    }

    private fun showDeleteConfirmationDialog(onPositiveClick: () -> Unit) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Подтверждение удаления")
            .setMessage("Вы действительно хотите удалить эту задачу?")
            .setPositiveButton("Да") { _, _ ->
                onPositiveClick()
            }
            .setNegativeButton("Нет") { _, _ -> }
            .create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}