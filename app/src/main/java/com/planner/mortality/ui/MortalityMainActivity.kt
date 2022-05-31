package com.planner.mortality.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.planner.mortality.R
import com.planner.mortality.databinding.ActivityMortalityMainBinding
import com.planner.mortality.utils.extensions.gone
import com.planner.mortality.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import logcat.logcat

@AndroidEntryPoint
class MortalityMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMortalityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMortalityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            logcat { "destination changed $destination with graph ${navController.graph}" }
            if (destination.id == R.id.fragmentRoutine || destination.id == R.id.fragmentTimer) {
                binding.bottomNavigation.visible()
            } else binding.bottomNavigation.gone()
        }
    }
}
