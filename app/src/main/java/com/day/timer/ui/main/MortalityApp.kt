package com.day.timer.ui.main

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.day.timer.R
import com.day.timer.ui.MortalityNavGraph
import com.day.timer.ui.components.InsetAwareTopAppBar
import com.day.timer.ui.theme.MortalityAppTheme
import com.day.timer.ui.theme.Red700
import com.google.accompanist.insets.ProvideWindowInsets
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Preview
@Composable
fun MortalityApp() {

    // Theme is the parent class -> ProvideWindowInsets
    // can remove theme if you don't consider the theming options, start with ProvideWindowInsets

    MortalityAppTheme {
        ProvideWindowInsets {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { MainAppBar() }
            ) {
                MortalityNavGraph(
                    scaffoldState = scaffoldState
                )
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