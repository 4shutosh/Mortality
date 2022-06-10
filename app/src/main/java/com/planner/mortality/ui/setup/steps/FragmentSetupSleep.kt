package com.planner.mortality.ui.setup.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.databinding.FragmentSetupSleepBinding
import com.planner.mortality.ui.setup.FragmentSetupViewModel
import dagger.hilt.android.AndroidEntryPoint
import logcat.logcat

@AndroidEntryPoint
class FragmentSetupSleep : Fragment() {

    private var _binding: FragmentSetupSleepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FragmentSetupViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSetupSleepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.sliderSleep.apply {
            minValue = 1
            maxValue = 10
            wrapSelectorWheel = true
            value = 8

            setOnValueChangedListener { p0, last, new ->
                logcat { "value $p0, $last, $new" }
                viewModel.actionSleepAmountSet(new)
            }

            setFormatter { input ->
                "$input hours"
            }
        }
    }

}