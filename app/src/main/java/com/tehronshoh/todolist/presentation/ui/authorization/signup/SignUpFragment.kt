package com.tehronshoh.todolist.presentation.ui.authorization.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.databinding.FragmentSignUpBinding
import com.tehronshoh.todolist.presentation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding
        get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initViewModel()
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it != null) findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
        }

        signUpClicked()
        navigateToSignIn()
    }

    private fun navigateToSignIn() {
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun signUpClicked() {
        signUpViewModel.signUpResult.observe(viewLifecycleOwner) {
            when (it) {
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
                signUpViewModel.createUser(
                    User(
                        email = emailInput.text.toString(), password = passwordInput.text.toString()
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}