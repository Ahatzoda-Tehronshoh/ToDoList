package com.tehronshoh.todolist.ui.authorization.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.databinding.FragmentSignInBinding
import com.tehronshoh.todolist.ui.MainViewModel
import com.tehronshoh.todolist.ui.util.MainViewModelFactory
import com.tehronshoh.todolist.ui.util.SignInViewModelFactory

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
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
            when(it) {
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

    private fun initViewModel() {
        val signInViewModelFactory =
            SignInViewModelFactory(ToDoDataSource.getInstance(requireContext()))

        //getting activity's viewmodel
        signInViewModel = ViewModelProvider(
            requireActivity(),
            signInViewModelFactory
        )[SignInViewModel::class.java]

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