package com.tehronshoh.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tehronshoh.todolist.data.ToDo
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.databinding.FragmentUpdateBinding
import com.tehronshoh.todolist.ui.util.MainViewModelFactory

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private val args: UpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setArgumentsToViews()
        updateButtonClick()
    }

    private fun setArgumentsToViews() {
        args.todo.apply {
            binding.title.setText(title)
            binding.description.setText(description)
        }
    }

    private fun updateButtonClick() {
        binding.updateButton.setOnClickListener {
            if (binding.title.text?.isNotBlank() == true) {
                val toDo = ToDo(
                    id = args.todo.id,
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString()
                )
                mainViewModel.updateToDo(toDo)
                findNavController().popBackStack()
            } else
                binding.titleInputLayout.error = "Title is required"
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