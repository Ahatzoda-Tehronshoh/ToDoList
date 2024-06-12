package com.tehronshoh.todolist.ui.authorization.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.databinding.FragmentSignUpBinding
import com.tehronshoh.todolist.ui.MainViewModel
import com.tehronshoh.todolist.ui.util.MainViewModelFactory
import com.tehronshoh.todolist.ui.util.SignUpViewModelFactory


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it != null)
                findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
        }

        signUpClicked()
        navigateToSignIn()
    }

    private fun navigateToSignIn() {
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun initViewModel() {
        val signUpViewModelFactory =
            SignUpViewModelFactory(ToDoDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        signUpViewModel = ViewModelProvider(
            requireActivity(),
            signUpViewModelFactory
        )[SignUpViewModel::class.java]


        val mainViewModelFactory =
            MainViewModelFactory(ToDoDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        mainViewModel = ViewModelProvider(
            requireActivity(),
            mainViewModelFactory
        )[MainViewModel::class.java]
    }


    private fun signUpClicked() {
        signUpViewModel.signUpResult.observe(viewLifecycleOwner) {
            when(it) {
                is SignUpResult.IsLoading -> binding.progressBar.visibility = View.VISIBLE
                is SignUpResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emailContainer.error = it.emailMessage
                    binding.passwordContainer.error = it.passwordMessage
                }
            }
        }

        binding.apply {
            signUpButton.setOnClickListener {
                signUpViewModel.createUser(User(
                    email = emailInput.text.toString(),
                    password = passwordInput.text.toString()
                ))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}