package com.tanishkej.onerep.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tanishkej.onerep.ui.workouts.WorkoutRoute
import java.net.URLDecoder

const val WORKOUT_ID_ARG = "workoutId"
const val WORKOUT_ROUTE = "workout_route"
private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

internal class WorkoutArgs(val workoutId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[WORKOUT_ID_ARG]), URL_CHARACTER_ENCODING))
}
fun NavController.navigateToWorkOut(workoutId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    val newRoute = "$WORKOUT_ROUTE/$workoutId"
    navigate(newRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.workoutScreen(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
) {
    composable(
        route = "workout_route/{$WORKOUT_ID_ARG}",
        arguments = listOf(
            navArgument(WORKOUT_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        WorkoutRoute(
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onWorkoutClick = onWorkoutClick,
        )
    }
}