package com.planner.mortality.ui.timer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.planner.mortality.R
import com.planner.mortality.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTimer : Fragment(R.layout.fragment_timer) {

    private val mainViewModel: MainViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}