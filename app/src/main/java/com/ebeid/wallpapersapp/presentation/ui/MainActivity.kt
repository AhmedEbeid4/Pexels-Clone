package com.ebeid.wallpapersapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.ActivityMainBinding
import com.ebeid.wallpapersapp.presentation.viewmodel.InternetConnectivityViewModel
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: InternetConnectivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[InternetConnectivityViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mgr: FragmentManager = supportFragmentManager
        val navHostFragment = mgr.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        viewModel.isConnected.observe(this) {
            if (it) {
                binding.contentLayout.show()
                binding.noInternetLayout.hide()
            } else {
                binding.contentLayout.hide()
                binding.noInternetLayout.show()
            }
        }
        navController = navHostFragment.findNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNavigationView.show()
                R.id.searchFragment -> binding.bottomNavigationView.show()
                R.id.savesFragment -> binding.bottomNavigationView.show()
                else -> binding.bottomNavigationView.hide()
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}