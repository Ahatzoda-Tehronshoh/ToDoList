package com.tehronshoh.todolist.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.LocalDataSource
import com.tehronshoh.todolist.databinding.SplashScreenFragmentBinding
import com.tehronshoh.todolist.presenter.viewmodel.factory.MainViewModelFactory
import com.tehronshoh.todolist.presenter.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenFragment: Fragment() {
    private var _binding: SplashScreenFragmentBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        loading()
    }

    private fun loading() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            withContext(Dispatchers.Main) {
                mainViewModel.loggedInUser.observe(viewLifecycleOwner) {
                    if (it != null)
                        findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
                    else
                        findNavController().navigate(R.id.action_splashScreenFragment_to_signUpFragment)

                    mainViewModel.loggedInUser.removeObservers(viewLifecycleOwner)
                }
            }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}