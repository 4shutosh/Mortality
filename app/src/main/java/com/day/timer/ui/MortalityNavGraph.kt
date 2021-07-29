package com.day.timer.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.day.timer.ui.MainDestinations.HOME_ROUTE
import com.day.timer.ui.home.HomeScreen
import kotlin.time.ExperimentalTime

object MainDestinations {
    const val HOME_ROUTE = "home"
}

@ExperimentalTime
@Composable
fun MortalityNavGraph(
    scaffoldState: ScaffoldState,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(HOME_ROUTE) {
            HomeScreen()
        }
    }
}