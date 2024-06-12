package com.tehronshoh.todolist.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.ui.MainViewModel
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.model.ToDoStatus
import com.tehronshoh.todolist.databinding.FragmentAddBinding
import com.tehronshoh.todolist.ui.util.MainViewModelFactory
import java.util.Arrays

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setStatusSpinner()
        addButtonClicked()
    }

    private fun setStatusSpinner() {
        val statuses = arrayOf(
            getString(R.string.waiting),
            getString(R.string.in_process),
            getString(R.string.done)
        )

        ArrayAdapter<CharSequence>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statuses,
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.statusSpinner.adapter = adapter
        }
    }

    private fun addButtonClicked() {
        binding.addButton.setOnClickListener {
            if (binding.title.text?.isNotBlank() == true) {
                val toDo = ToDo(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    dueDate = System.currentTimeMillis(),
                    status = getStatus(binding.statusSpinner.selectedItem.toString()),
                    userId = mainViewModel.loggedInUser.value?.id ?: 0L
                )
                mainViewModel.createToDo(toDo)
                findNavController().popBackStack()
            } else
                binding.titleInputLayout.error = "Title is required"
        }
    }

    private fun getStatus(text: String): String {
        return when (text) {
            getString(R.string.waiting) -> ToDoStatus.WAITING.toString()
            getString(R.string.in_process) -> ToDoStatus.IN_PROCESS.toString()
            getString(R.string.done) -> ToDoStatus.DONE.toString()
            else -> "WAITING"
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}