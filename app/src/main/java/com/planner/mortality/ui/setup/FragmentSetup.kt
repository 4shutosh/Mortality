package com.planner.mortality.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.planner.mortality.R
import com.planner.mortality.databinding.FragmentSetupBinding
import com.planner.mortality.ui.setup.steps.FragmentSetupAbout
import com.planner.mortality.ui.setup.steps.FragmentSetupBirthday
import com.planner.mortality.ui.setup.steps.FragmentSetupDeath
import com.planner.mortality.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSetup : Fragment() {

    private lateinit var binding: FragmentSetupBinding

    private val viewModel: FragmentSetupViewModel by viewModels({ this })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSetupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        activity?.onBackPressedDispatcher?.addCallback(onBackPress)

        binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@FragmentSetup, listOfChildPages)
            isUserInputEnabled = false
            registerOnPageChangeCallback(onPageChangeCallback)
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
            tab.select()
        }.attach()

        binding.ctaProceed.setOnClickListener {
            viewModel.actionCtaButtonClicked(binding.viewPager.currentItem)
        }
    }

    private fun setUpObservers() {
        viewModel.command.observe(viewLifecycleOwner) {
            processCommand(it)
        }
    }

    private fun processCommand(it: FragmentSetupViewModel.Command) {
        when (it) {
            is FragmentSetupViewModel.Command.MoveToPage -> {
                binding.viewPager.currentItem = if (it.index == -1) {
                    binding.viewPager.currentItem + 1
                } else it.index
                binding.ctaProceed.isEnabled = it.enableCta
            }
            is FragmentSetupViewModel.Command.EnableCta -> {
                binding.ctaProceed.isEnabled = it.enableCta
            }
            is FragmentSetupViewModel.Command.Notify -> {
                Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
            }
            FragmentSetupViewModel.Command.EndSetupProcess -> {
                val navController = findNavController()
                navController.navigate(R.id.action_setup_to_main)
            }
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.actionViewPagerPageChanged(position)
            binding.ctaProceed.text = ctaButtonText[position]
        }
    }

    override fun onDestroy() {
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        super.onDestroy()
    }

    private val onBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.viewPager.currentItem == 0) {
                activity?.onBackPressed()
            } else {
                binding.viewPager.currentItem -= 1
            }
        }
    }

    companion object {
        val listOfChildPages =
            listOf(FragmentSetupAbout(), FragmentSetupBirthday(), FragmentSetupDeath())
        val ctaButtonText = listOf("Get Started", "Proceed →", "Next Step →")
    }
}