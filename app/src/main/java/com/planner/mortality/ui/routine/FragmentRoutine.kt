package com.planner.mortality.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.R
import com.planner.mortality.databinding.FragmentRoutineBinding
import com.planner.mortality.ui.MainViewModel
import com.planner.mortality.utils.MortalityPicker
import com.planner.mortality.utils.MortalityTimeConsumedPercentage
import dagger.hilt.android.AndroidEntryPoint
import logcat.logcat
import java.time.Instant

@AndroidEntryPoint
class FragmentRoutine : Fragment(R.layout.fragment_routine) {

    private val mainViewModel: MainViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private val viewModel: RoutineViewModel by viewModels()

    private lateinit var binding: FragmentRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpObservers()
    }

    private fun setupViews() {
        binding.fabAddButton.setOnClickListener {
            viewModel.actionRoutineFabClicked()
        }
    }

    private fun setUpObservers() {
        viewModel.command.observe(viewLifecycleOwner) {
            when (it) {
                is RoutineViewModel.Command.ShowDatePickerDialog -> {
                    MortalityPicker().showPastFutureDatePickerDialog(
                        R.string.select_deadline_date,
                        childFragmentManager,
                        Instant.now().epochSecond,
                        onSelected = {
                            viewModel.actionTimeSetByUser()
                        }
                    )
                }
            }
        }

        mainViewModel.deathTimerPercentage.observe(viewLifecycleOwner) {
            logcat { "found percentage $it" }
            setDeathProgress(it)
        }
    }

    private fun setDeathProgress(mortalityTime: MortalityTimeConsumedPercentage) {
        binding.deathProgress.apply {
            progressCircular6.progress = mortalityTime.years.toInt()
            progressCircular5.progress = mortalityTime.months.toInt()
            progressCircular4.progress = mortalityTime.days.toInt()
            progressCircular3.progress = mortalityTime.hours.toInt()
            progressCircular2.progress = mortalityTime.minutes.toInt()
            progressCircular1.progress = mortalityTime.seconds.toInt()
        }
    }


}