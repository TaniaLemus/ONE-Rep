package com.tanishkej.onerep.ui.workouts.screens

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tanishkej.onerep.ui.navigation.WORKOUT_ID_ARG
import com.tanishkej.onerep.ui.navigation.WORKOUT_ROUTE
import com.tanishkej.onerep.ui.navigation.navigateToWorkOut
import com.tanishkej.onerep.ui.navigation.workoutScreen
import com.tanishkej.onerep.ui.theme.OneRepTheme

private const val DETAIL_PANE_NAV_HOST_ROUTE = "detail_pane_route"

const val WORKOUT_LIST_PANE_BASE = "workout_list_route"
const val WORKOUT_LIST_PANE_ROUTE = "$WORKOUT_LIST_PANE_BASE?$WORKOUT_ID_ARG={$WORKOUT_ID_ARG}"

fun NavGraphBuilder.workoutListDetailPaneScreenScreen() {
    composable(
        route = WORKOUT_LIST_PANE_ROUTE,
        arguments = listOf(
            navArgument(WORKOUT_ID_ARG) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            },
        ),
    ) {
        WorkoutListDetailPaneScreen()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun WorkoutListDetailPaneScreen(modifier: Modifier = Modifier) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator()
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }
    val nestedNavController = rememberNavController()
    fun onWorkoutClickShowDetail(workoutId: String) {
        nestedNavController.navigateToWorkOut(workoutId) {
            popUpTo(DETAIL_PANE_NAV_HOST_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            WorkoutListRoute(
                onWorkoutClick = ::onWorkoutClickShowDetail,
                modifier = modifier
            )
        },
        detailPane = {
            NavHost(
                navController = nestedNavController,
                startDestination = WORKOUT_ROUTE,
                route = DETAIL_PANE_NAV_HOST_ROUTE
            ) {
                workoutScreen(
                    showBackButton = !listDetailNavigator.isListPaneVisible(),
                    onBackClick = listDetailNavigator::navigateBack,
                    onWorkoutClick = ::onWorkoutClickShowDetail,
                )
                composable(route = WORKOUT_ROUTE) {
                    Text(text = "Choose a workout to Start")
                }
            }
        },
    )
    LaunchedEffect(Unit) {
        if (!listDetailNavigator.isListPaneVisible()) onWorkoutClickShowDetail("Barbell Bench Press")
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OneRepTheme {
        WorkoutListDetailPaneScreen()
    }
}
