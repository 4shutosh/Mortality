package com.day.timer.ui.main

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.day.timer.R
import com.day.timer.ui.components.InsetAwareTopAppBar
import com.day.timer.ui.theme.MortalityAppTheme
import com.day.timer.ui.theme.Red700
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun MortalityApp() {

    // Theme is the parent class -> ProvideWindowInsets
    // can remove theme if you don't consider the theming options, start with ProvideWindowInsets

    MortalityAppTheme {
        ProvideWindowInsets {
            Scaffold(
                topBar = { MainAppBar() }
            ) {
                ConstraintLayout {
                    val viewModel = hiltViewModel<MainViewModel>()
                    val textTimer = viewModel.timerText.observeAsState()
                    Text(text = "Kya bolte bidu")
                    viewModel.startBackgroundServiceTimer(1624986116000)
                }
            }
        }

    }
}

// app bar i.e the toolbar
@Composable
fun MainAppBar() {
    InsetAwareTopAppBar(
        backgroundColor = Red700,
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}