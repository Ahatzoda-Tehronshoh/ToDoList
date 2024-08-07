package com.tehronshoh.todolist.presentation.ui


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.tehronshoh.todolist.R
import com.tehronshoh.todolist.databinding.ActivityMainBinding
import com.tehronshoh.todolist.presentation.util.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //initViewModel()

        setSupportActionBar(binding.mainToolbar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navControllerControl()
        // startWorkManager()
    }

    private fun startWorkManager() {
        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
            flexTimeInterval = 10,
            flexTimeIntervalUnit = TimeUnit.MINUTES
        ).build()

        val workManager = WorkManager.getInstance(this)
        workManager.cancelAllWork()

        workManager.getWorkInfoByIdLiveData(notificationRequest.id)
            .observe(this) {
                Log.d("TAG_TEST", "Work status: ${it.state}")
            }
        workManager.enqueue(notificationRequest)
    }

    /** permission for push notifications
    private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
    if (isGranted) {
    // FCM SDK (and your app) can post notifications.

    }
    }

    private fun askNotificationPermission() {
    // This is only necessary for API level >= 33 (TIRAMISU)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
    PackageManager.PERMISSION_GRANTED
    ) {
    // FCM SDK (and your app) can post notifications.
    } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
    // TODO: display an educational UI explaining to the user the features that will be enabled
    //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
    //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
    //       If the user selects "No thanks," allow the user to continue without notifications.
    } else {
    // Directly ask for the permission
    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
    }
    }*/

    private fun navControllerControl() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    binding.mainToolbar.navigationIcon = null
                    binding.mainToolbar.visibility = VISIBLE
                }

                R.id.addFragment, R.id.updateFragment -> {
                    binding.mainToolbar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.back_button)
                    binding.mainToolbar.setNavigationOnClickListener {
                        navController.popBackStack()
                    }
                    binding.mainToolbar.visibility = VISIBLE
                }

                else -> binding.mainToolbar.visibility = GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_data -> {
                Toast.makeText(this, mainViewModel.loggedInUser.value?.email, Toast.LENGTH_SHORT)
                    .show()
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