package com.tehronshoh.todolist.presentation.ui.add_update

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.datasource.LocalDataSource
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.data.model.ToDoStatus
import com.tehronshoh.todolist.databinding.CommentsBottomSheetDialogBinding
import com.tehronshoh.todolist.databinding.FragmentUpdateBinding
import com.tehronshoh.todolist.databinding.HistoryBottomSheetDialogBinding
import com.tehronshoh.todolist.presentation.ui.add_update.adapter.ToDoCommentsRecyclerViewAdapter
import com.tehronshoh.todolist.presentation.ui.add_update.adapter.ToDoHistoryRecyclerViewAdapter
import com.tehronshoh.todolist.presentation.util.FCMSender
import com.tehronshoh.todolist.presentation.util.getDateString
import com.tehronshoh.todolist.presentation.util.factory.EditViewModelFactory
import com.tehronshoh.todolist.presentation.util.factory.MainViewModelFactory
import com.tehronshoh.todolist.presentation.ui.MainViewModel
import java.util.Calendar

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var editViewModel: EditViewModel

    private val args: UpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        authorizationListener()
        setupDatePicker()
        setStatusSpinner()
        setAssigneeSpinner()
        setArgumentsToViews()
        updateButtonClick()
        historyButtonListener()
        commentsButtonListener()

        setViewsEditable()
    }

    private fun historyButtonListener() {
        binding.history.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val historyBottomSheetDialogBinding =
                HistoryBottomSheetDialogBinding.inflate(layoutInflater, null, false)
            setupHistoryRecyclerView(historyBottomSheetDialogBinding)
            dialog.setContentView(historyBottomSheetDialogBinding.root)
            dialog.show()
        }
    }

    private fun setupHistoryRecyclerView(historyBottomSheetDialogBinding: HistoryBottomSheetDialogBinding) {
        val historyAdapter = ToDoHistoryRecyclerViewAdapter()
        historyBottomSheetDialogBinding.historyRv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = historyAdapter
        }
        editViewModel.getToDoHistory(args.todo.id).observe(viewLifecycleOwner) {
            if (it.isEmpty()) historyBottomSheetDialogBinding.historyEmptyText.visibility =
                View.VISIBLE
            else historyBottomSheetDialogBinding.historyEmptyText.visibility = View.GONE

            historyAdapter.submitList(it)
        }
    }

    private fun commentsButtonListener() {
        binding.comments.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val commentsBottomSheetDialogBinding =
                CommentsBottomSheetDialogBinding.inflate(layoutInflater, null, false)

            setupCommentsRecyclerView(commentsBottomSheetDialogBinding)

            if (mainViewModel.loggedInUser.value?.email == args.todo.creatorEmail || mainViewModel.loggedInUser.value?.email == args.todo.assigneeEmail) {
                commentsBottomSheetDialogBinding.commentsEditText.isEnabled = true

                commentsBottomSheetDialogBinding.sendButton.setOnClickListener {
                    if (commentsBottomSheetDialogBinding.commentsEditText.text?.isNotBlank() == true) {
                        editViewModel.insertToDoComments(
                            ToDoComments(
                                todoId = args.todo.id,
                                comment = commentsBottomSheetDialogBinding.commentsEditText.text.toString(),
                                from = mainViewModel.loggedInUser.value?.email.toString(),
                                date = Calendar.getInstance().getDateString()
                            )
                        )
                        commentsBottomSheetDialogBinding.commentsEditText.text.clear()
                    }
                }
            } else commentsBottomSheetDialogBinding.commentsEditText.isEnabled = false

            dialog.setContentView(commentsBottomSheetDialogBinding.root)
            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            dialog.show()
        }
    }

    private fun setupCommentsRecyclerView(commentsBottomSheetDialogBinding: CommentsBottomSheetDialogBinding) {
        val commentsAdapter = ToDoCommentsRecyclerViewAdapter(args.todo.creatorEmail)
        commentsBottomSheetDialogBinding.commentsRv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = commentsAdapter
        }
        editViewModel.getAllToDoComments(args.todo.id).observe(viewLifecycleOwner) {
            if (it.isEmpty()) commentsBottomSheetDialogBinding.commentsEmptyText.visibility =
                View.VISIBLE
            else commentsBottomSheetDialogBinding.commentsEmptyText.visibility = View.GONE

            commentsAdapter.submitList(it)
        }
    }


    private fun authorizationListener() {
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.action_updateFragment_to_signUpFragment)
            }
        }
    }

    private fun setViewsEditable() {
        val taskIsNotClosed = ToDoStatus.valueOf(args.todo.status) != ToDoStatus.CLOSED
        val areUserCreator = mainViewModel.loggedInUser.value?.email == args.todo.creatorEmail
        val areUserAssignee =
            mainViewModel.loggedInUser.value?.email == args.todo.assigneeEmail && taskIsNotClosed

        binding.apply {
            allowText.text =
                if (!areUserCreator && !areUserAssignee)
                    requireContext().getString(R.string.you_can_not_edit)
                else if (!areUserCreator)
                    requireContext().getString(R.string.you_can_status_edit)
                else
                    ""
            titleInputLayout.isEnabled = areUserCreator
            descriptionInputLayout.isEnabled = areUserCreator
            assigneeSpinner.isEnabled = areUserCreator
            dueDateInputLayout.isEnabled = areUserCreator

            statusSpinner.isEnabled = areUserCreator || areUserAssignee
            updateButton.isEnabled = areUserCreator || areUserAssignee
        }
    }

    private fun setArgumentsToViews() {
        args.todo.apply {
            binding.title.setText(title)
            binding.description.setText(description)
            binding.statusSpinner.setSelection(getStatusTextIndex(ToDoStatus.valueOf(args.todo.status)))
            binding.dueDate.setText(dueDate)
        }
    }

    private fun updateButtonClick() {
        binding.updateButton.setOnClickListener {
            if (binding.title.text?.isNotBlank() == true) {
                val toDo = ToDo(
                    id = args.todo.id,
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    dueDate = binding.dueDate.text.toString(),
                    status = getStatus(binding.statusSpinner.selectedItem.toString()),
                    creatorEmail = args.todo.creatorEmail,
                    assigneeEmail = binding.assigneeSpinner.selectedItem.toString()
                )
                mainViewModel.updateToDo(toDo, args.todo)
                sendPushNotification(toDo)
                findNavController().popBackStack()
            } else binding.titleInputLayout.error = "Title is required"
        }
    }

    private fun sendPushNotification(toDo: ToDo) {
        FirebaseMessaging.getInstance().subscribeToTopic(toDo.id.toString())

        FCMSender(
            "/topics/${toDo.id}",
            if (mainViewModel.loggedInUser.value?.email == toDo.creatorEmail) toDo.assigneeEmail
            else toDo.creatorEmail,
            "Task:${toDo.title} was updated!",
            requireContext(),
            requireActivity()
        ).sendNotifications()
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

    private fun getStatusTextIndex(status: ToDoStatus): Int {
        return when (status) {
            ToDoStatus.WAITING -> 0
            ToDoStatus.IN_PROCESS -> 1
            ToDoStatus.DONE -> 2
            ToDoStatus.CLOSED -> 3
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
                binding.assigneeSpinner.setSelection(adapter.getPosition(args.todo.assigneeEmail))
            }
        }

    private fun setupDatePicker() {
        val date = args.todo.dueDate.split("-")
        var year = date[2].toInt()
        var month = date[1].toInt() - 1
        var day = date[0].toInt()

        binding.dueDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    year = selectedYear
                    month = selectedMonth
                    day = selectedDay

                    val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                    binding.dueDate.setText(selectedDate)
                }, year, month, day
            )
            datePickerDialog.datePicker.minDate =
                Calendar.getInstance().timeInMillis // Только будущие даты
            datePickerDialog.show()
        }
    }

    private fun setStatusSpinner() {
        val statuses = mutableListOf(
            getString(R.string.waiting), getString(R.string.in_process), getString(R.string.done)
        )

        if (mainViewModel.loggedInUser.value?.email == args.todo.creatorEmail || ToDoStatus.valueOf(
                args.todo.status
            ) == ToDoStatus.CLOSED
        ) statuses.add(getString(R.string.closed))

        ArrayAdapter<CharSequence>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            statuses.toTypedArray(),
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.statusSpinner.adapter = adapter
        }
    }

    private fun initViewModel() {
        val mainViewModelFactory =
            MainViewModelFactory(LocalDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        mainViewModel = ViewModelProvider(
            requireActivity(), mainViewModelFactory
        )[MainViewModel::class.java]

        val editViewModelFactory =
            EditViewModelFactory(LocalDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        editViewModel = ViewModelProvider(
            requireActivity(), editViewModelFactory
        )[EditViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}