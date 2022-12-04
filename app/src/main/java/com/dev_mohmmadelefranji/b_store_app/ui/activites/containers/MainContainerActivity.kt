package com.dev_mohmmadelefranji.b_store_app.ui.activites.containers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.ActivityMainContainerBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.NotificationRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.fragments.home.HomeFragment
import com.dev_mohmmadelefranji.b_store_app.utils.Constants
import com.dev_mohmmadelefranji.b_store_app.utils.Constants.CHANNEL_ID
import com.dev_mohmmadelefranji.b_store_app.utils.NotificationViewModel
import kotlin.random.Random

class MainContainerActivity : AppCompatActivity() {
    /*----------------------------------*/

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener

    private lateinit var notificationViewModel: NotificationViewModel

    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        this.getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    /*----------------------------------*/
    private lateinit var binding: ActivityMainContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_container)

        //region createFactory
        val factory = NotificationViewModel(NotificationRepository()).createFactory()
        notificationViewModel = ViewModelProvider(this, factory)[NotificationViewModel::class.java]
        //endregion

        notificationViewModel.getAllNotifications(getUserToken())

        //  observeGetAllNotifications()

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            navGraph = navController.graph,
            //   drawerLayout = binding.drawerLayout

        )



        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.homeFragment -> {
                        binding.bottomNavigationView.show()
                    }
                    R.id.favoriteProductsFragment -> {
                        binding.bottomNavigationView.show()
                        binding.toolbar.navigationIcon = null
                    }
                    R.id.cartFragment -> {
                        binding.bottomNavigationView.show()
                        binding.toolbar.navigationIcon = null
                    }
                    R.id.profileFragment -> {
                        binding.bottomNavigationView.show()
                        binding.toolbar.navigationIcon = null
                    }

                    R.id.orderFragment -> {

                    }
                    /*  R.id.searchFragment,
                      R.id.notificationFragment,
                      R.id.chartFragment -> {
                          binding.bottomAppBar.show()
                          binding.bottomNavigationView.show()
                          binding.fabAddNote.show()
                      }*/
                    else -> {
                        binding.bottomNavigationView.hide()

                    }
                }
            }

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigationView.setupWithNavController(navController = navController)
        // binding.navigationView.setupWithNavController(navController = navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener = listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener = listener)
    }

    private fun getUserToken(): String {
        return sharedPreferences.getString(Constants.KEY_TOKEN, null)!!
    }

    private fun observeGetAllNotifications() {

        Log.d(HomeFragment.TAG, "observeGetDataOfHome")
        lifecycleScope.launchWhenCreated {
            notificationViewModel.getAllNotifications.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> Unit
                    is ApiResponse.Success -> {

                        it.data?.let { data ->

                            if (data.notificationList.isNotEmpty()) {


                                val notificationManager =
                                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                                val notificationID = Random.nextInt()

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    createNotificationChannel(notificationManager)
                                }

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                                val random = System.currentTimeMillis().toInt()
                                val pendingIntent = PendingIntent.getActivity(
                                    this@MainContainerActivity, random, intent,
                                    PendingIntent.FLAG_ONE_SHOT
                                )

                                for (i in data.notificationList) {
                                    val notification = NotificationCompat.Builder(
                                        this@MainContainerActivity,
                                        CHANNEL_ID
                                    )


                                        .setContentTitle(i.title)
                                        .setContentText(i.body)
                                        .setSmallIcon(R.drawable.logo)
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent)
                                        .setStyle(
                                            NotificationCompat.BigTextStyle()
                                                .bigText(i.body)
                                        )
                                        .build()
                                    notificationManager.notify(notificationID, notification)
                                }


                            }


                        }
                    }
                    is ApiResponse.Failure -> Unit
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {

        val channelName = "channelName"
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }
}