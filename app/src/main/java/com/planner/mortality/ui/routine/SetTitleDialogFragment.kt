package com.planner.mortality.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.planner.mortality.databinding.DialogSetTitleBinding

class SetTitleDialogFragment : DialogFragment() {

    private val viewModel: RoutineViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private lateinit var binding: DialogSetTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogSetTitleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {

    }


}