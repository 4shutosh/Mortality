package com.planner.mortality.ui.setup.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.R
import com.planner.mortality.databinding.FragmentSetupDayEndBinding
import com.planner.mortality.ui.setup.FragmentSetupViewModel
import com.planner.mortality.utils.MortalityPicker
import com.planner.mortality.utils.formatMinutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSetupDayEnd : Fragment() {

    private var _binding: FragmentSetupDayEndBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FragmentSetupViewModel by viewModels({ requireParentFragment() })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSetupDayEndBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.cardTime.setOnClickListener {
            MortalityPicker().showTimePickerDialog(
                R.string.pick_time,
                childFragmentManager,
                initialTime = tenThirtyPM,
                {
                    binding.tvTextInput.text = "${it.hour}:${it.minute.formatMinutes()}"
                    viewModel.actionUserDayEndTimeSet(it.hour, it.minute)
                }
            )
        }
    }

    // handle edge cases of time picker here: disable am selection etc

    companion object {
        const val tenThirtyPM = 1656541800000
    }
}