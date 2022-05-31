package com.planner.mortality.ui.setup.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.R
import com.planner.mortality.databinding.FragmentSetupBirthdayBinding
import com.planner.mortality.ui.setup.FragmentSetupViewModel
import com.planner.mortality.utils.MortalityPicker
import com.planner.mortality.utils.getFormattedDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSetupBirthday : Fragment() {

    private lateinit var binding: FragmentSetupBirthdayBinding

    private val viewModel: FragmentSetupViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSetupBirthdayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        binding.cardDate.setOnClickListener {
            MortalityPicker().showPastDatePickerDialog(
                R.string.pick_your_birthdate,
                onSelected = {
                    viewModel.actionBirthDateSet(it)
                    binding.tvTextInput.text = getFormattedDate(it)
                },
                fragmentManager = childFragmentManager
            )
        }
    }
}
