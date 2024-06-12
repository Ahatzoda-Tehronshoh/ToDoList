package com.tehronshoh.todolist.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.databinding.ActivityMainBinding
import com.tehronshoh.todolist.ui.util.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initViewModel()

        setSupportActionBar(binding.mainToolbar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
    }


    private fun initViewModel() {
        val mainViewModelFactory =
            MainViewModelFactory(ToDoDataSource.getInstance(this))

        //getting activity's viewmodel
        mainViewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        )[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_data -> {
                Toast.makeText(this, mainViewModel.loggedInUser.value?.email, Toast.LENGTH_SHORT).show()
            }

            R.id.action_log_out -> {
                mainViewModel.logOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}