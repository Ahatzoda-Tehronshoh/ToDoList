package com.tehronshoh.todolist.presenter.add

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.presenter.viewmodel.MainViewModel
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.LocalDataSource
import com.tehronshoh.todolist.data.model.ToDoStatus
import com.tehronshoh.todolist.databinding.FragmentAddBinding
import com.tehronshoh.todolist.presenter.viewmodel.factory.EditViewModelFactory
import com.tehronshoh.todolist.presenter.viewmodel.factory.MainViewModelFactory
import java.util.Calendar

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var editViewModel: EditViewModel

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

        authorizationListener()
        setupDatePicker()
        setStatusSpinner()
        setAssigneeSpinner()
        addButtonClicked()
    }

    private fun authorizationListener() {
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.action_addFragment_to_signUpFragment)
            }
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.dueDate.setText("$day-${month + 1}-$year")
        binding.dueDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                    binding.dueDate.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.datePicker.minDate = calendar.timeInMillis // Только будущие даты
            datePickerDialog.show()
        }
    }

    private fun setAssigneeSpinner() =
        editViewModel.getAllUsersEmail().observe(viewLifecycleOwner) { usersEmail ->
            ArrayAdapter<CharSequence>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                usersEmail,
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.assigneeSpinner.adapter = adapter
            }
        }

    private fun setStatusSpinner() {
        val statuses = arrayOf(
            getString(R.string.waiting),
            getString(R.string.in_process),
            getString(R.string.done),
            getString(R.string.closed),
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
                    dueDate = binding.dueDate.text.toString(),
                    status = getStatus(binding.statusSpinner.selectedItem.toString()),
                    creatorEmail = mainViewModel.loggedInUser.value?.email ?: "",
                    assigneeEmail = binding.assigneeSpinner.selectedItem.toString()
                )
                mainViewModel.createToDo(toDo)

                FirebaseMessaging.getInstance().subscribeToTopic(toDo.id.toString())
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
            getString(R.string.closed) -> ToDoStatus.CLOSED.toString()
            else -> "WAITING"
        }
    }

    private fun initViewModel() {
        val mainViewModelFactory =
            MainViewModelFactory(LocalDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        )[MainViewModel::class.java]

        val editViewModelFactory =
            EditViewModelFactory(LocalDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        editViewModel = ViewModelProvider(
            requireActivity(),
            editViewModelFactory
        )[EditViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}