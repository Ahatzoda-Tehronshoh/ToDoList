package com.tehronshoh.todolist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.databinding.SplashScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private var _binding: SplashScreenFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading()
    }

    private fun loading() {
        lifecycleScope.launch {
            delay(1500)
            mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
                if (it != null)
                    findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
                else
                    findNavController().navigate(R.id.action_splashScreenFragment_to_signUpFragment)

                mainViewModel.loggedInUser.removeObservers(viewLifecycleOwner)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}