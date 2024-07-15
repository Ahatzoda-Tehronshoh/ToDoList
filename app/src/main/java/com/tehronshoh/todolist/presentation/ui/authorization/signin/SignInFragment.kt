package com.tehronshoh.todolist.presentation.ui.authorization.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.databinding.FragmentSignInBinding
import com.tehronshoh.todolist.presentation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding
        get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initViewModel()
        mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (it != null)
                findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
        }

        signInClicked()
        navigateToSignUp()
    }

    private fun navigateToSignUp() {
        binding.registerText.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signInClicked() {
        signInViewModel.signInResult.observe(viewLifecycleOwner) {
            when (it) {
                is SignInResult.IsLoading -> binding.progressBar.visibility = View.VISIBLE
                is SignInResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.emailContainer.error = it.message
                    binding.passwordContainer.error = it.message
                }
            }
        }

        binding.signInButton.setOnClickListener {
            signInViewModel.isUserWithExists(
                email = binding.emailInput.text.toString(),
                password = binding.passwordInput.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}