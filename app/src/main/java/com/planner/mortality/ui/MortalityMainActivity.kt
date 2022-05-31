package com.planner.mortality.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.planner.mortality.R
import com.planner.mortality.databinding.ActivityMortalityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }
}
