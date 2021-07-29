package com.day.timer.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Preview("HomeScreen")
@Composable
fun HomeScreen() {

    val context = LocalContext.current

    ConstraintLayout {
        val viewModel = hiltViewModel<HomeViewModel>()

        viewModel.toast.observe(LocalLifecycleOwner.current) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        val timerText = viewModel.timerText.observeAsState(initial = 999)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = timerText.value.toString())
        }
    }

}