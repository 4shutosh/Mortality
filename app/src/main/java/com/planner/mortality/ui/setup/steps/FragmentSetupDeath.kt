package com.planner.mortality.ui.setup.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.R
import com.planner.mortality.databinding.FragmentSetupDeathBinding
import com.planner.mortality.ui.setup.FragmentSetupViewModel
import dagger.hilt.android.AndroidEntryPoint
import logcat.logcat

@AndroidEntryPoint
class FragmentSetupDeath : Fragment(R.layout.fragment_setup_death) {

    private var _binding: FragmentSetupDeathBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FragmentSetupViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSetupDeathBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
    }

    private fun setUpObservers() {
        viewModel.userAgeLiveData.observe(viewLifecycleOwner) { age ->
            logcat { "age found $age" }
            binding.sliderAge.progress = age
            binding.sliderAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekbar: SeekBar?,
                    value: Int,
                    fromUser: Boolean,
                ) {
                    if (value <= age) {
                        binding.sliderAge.progress = age
                        val seekbarText = "$age years"
                        binding.tvTextInput.text = seekbarText
                        viewModel.actionLifeSpanSet(age)
                    } else {
                        val seekbarText = "$value years"
                        binding.tvTextInput.text = seekbarText
                        viewModel.actionLifeSpanSet(value)
                    }

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}

            })
        }
    }


}