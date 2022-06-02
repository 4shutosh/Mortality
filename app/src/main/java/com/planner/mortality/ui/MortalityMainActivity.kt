package com.planner.mortality.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
        logcat { "setup views" }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val isUserSetupDone = intent.getBooleanExtra(USER_SETUP_DONE_KEY, false)
        if (!isUserSetupDone) {
            navController.navigate(R.id.to_nav_setup)
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            logcat { "destination changed $destination with graph ${navController.graph}" }
            if (destination.id == R.id.fragmentRoutine || destination.id == R.id.fragmentTimer) {
                binding.bottomNavigation.visible()
                binding.bottomNavigation.setupWithNavController(navController)
            } else binding.bottomNavigation.gone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logcat { "onDestroy ${this.localClassName}" }
    }

    companion object {

        private const val USER_SETUP_DONE_KEY = "USER_SETUP_DONE_KEY"

        fun instance(
            context: Context,
            userSetupDone: Boolean,
        ): Intent {
            val intent = Intent(context, MortalityMainActivity::class.java)
            intent.putExtra(USER_SETUP_DONE_KEY, userSetupDone)
            return intent
        }
    }
}
