package com.planner.mortality.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.planner.mortality.R
import com.planner.mortality.databinding.ActivityMortalityMainBinding
import com.planner.mortality.ui.holder.HolderActivity
import com.planner.mortality.ui.setup.FragmentSetup
import com.planner.mortality.utils.extensions.bindTime
import com.planner.mortality.utils.extensions.gone
import com.planner.mortality.utils.extensions.showToast
import com.planner.mortality.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MortalityMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMortalityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMortalityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val isUserSetupDone = intent.getBooleanExtra(USER_SETUP_DONE_KEY, false)
        if (!isUserSetupDone) {
            // move again to setup fragment to holder activity
            val setupScreenIntent =
                HolderActivity.intent(this, FragmentSetup.FRAGMENT_SETUP_KEY)
            setupScreenIntent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(setupScreenIntent)
            finish()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragmentRoutine || destination.id == R.id.fragmentTimer) {
                binding.bottomNavigation.visible()
                binding.bottomNavigation.setupWithNavController(navController)
            } else binding.bottomNavigation.gone()
        }

        binding.tvDeathTimer.rootLl.setOnClickListener {
            viewModel.actionUserDeathTimerClicked()
        }
    }

    private fun setupObservers() {
        viewModel.command.observe(this) {
            processCommand(it)
        }

        viewModel.deathTimerText.observe(this) {
            binding.tvDeathTimer.bindTime(it)
        }
    }

    private fun processCommand(it: MainViewModel.Command) {
        when (it) {
            is MainViewModel.Command.ShowToast -> showToast(it.message)
        }
    }


    companion object {

        private const val USER_SETUP_DONE_KEY = "USER_SETUP_DONE_KEY"

        fun intent(
            context: Context,
            userSetupDone: Boolean,
        ): Intent {
            val intent = Intent(context, MortalityMainActivity::class.java)
            intent.putExtra(USER_SETUP_DONE_KEY, userSetupDone)
            return intent
        }
    }
}
