package com.tanishkej.onerep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tanishkej.onerep.ui.workouts.screens.WORKOUT_LIST_PANE_ROUTE
import com.tanishkej.onerep.ui.workouts.screens.workoutListDetailPaneScreenScreen

@Composable
fun OneRepNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String = WORKOUT_LIST_PANE_ROUTE
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        workoutListDetailPaneScreenScreen()
    }
}