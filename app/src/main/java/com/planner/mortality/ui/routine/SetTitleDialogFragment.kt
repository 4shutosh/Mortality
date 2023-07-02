package com.planner.mortality.ui.routine

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.planner.mortality.databinding.DialogSetTitleBinding
import dagger.hilt.android.AndroidEntryPoint
import logcat.logcat

@AndroidEntryPoint
class SetTitleDialogFragment : DialogFragment() {

    private val viewModel: RoutineViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    companion object {
        const val SET_TITLE_DIALOG_KEY = "SET_TITLE_DIALOG_KEY"
    }

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
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.textInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                logcat { "text watcher $editable" }
                viewModel.validateInputText(editable.toString())
            }

        })
        binding.btnSetTitle.setOnClickListener {
            viewModel.actionCreateTimer(binding.textInput.text.toString())
        }
    }

    private fun setUpObservers() {
        viewModel.command.observe(viewLifecycleOwner) {
            logcat { "found command $it" }
            when (it) {
                is RoutineViewModel.Command.EnableSetTitleButton -> {
                    logcat { "found it enable ${it.enable}" }
                    binding.btnSetTitle.isEnabled = it.enable
                }
                is RoutineViewModel.Command.DismissDialog -> dismissAllowingStateLoss()
            }
        }
    }


}