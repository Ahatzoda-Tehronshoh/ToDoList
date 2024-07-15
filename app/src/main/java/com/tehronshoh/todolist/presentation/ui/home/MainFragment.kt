package com.tehronshoh.todolist.presentation.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.databinding.FragmentMainBinding
import com.tehronshoh.todolist.presentation.ui.MainViewModel
import com.tehronshoh.todolist.presentation.ui.home.adapter.ToDoRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of To_Do.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

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

        setupRecyclerView()
        addButtonClick()
        authorizationListener()
    }

    private fun authorizationListener() {
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.action_mainFragment_to_signUpFragment)
            }
        }
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
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = toDoAdapter.also {
                it.onItemClickListener = { toDo ->
                    val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(toDo)
                    findNavController().navigate(action)
                }
                it.onDeleteListener = { toDo ->
                    if (mainViewModel.loggedInUser.value?.email == toDo.creatorEmail)
                        showDeleteConfirmationDialog {
                            mainViewModel.deleteToDo(toDo)
                            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                        }
                    else
                        Toast.makeText(
                            requireContext(),
                            context.getString(R.string.you_can_not_edit), Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.toDos.collectLatest { toDoList ->
                    withContext(Dispatchers.Main) {
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