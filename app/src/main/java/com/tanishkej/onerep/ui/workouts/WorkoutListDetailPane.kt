package com.tanishkej.onerep.ui.workouts

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tanishkej.onerep.ui.navigation.WORKOUT_ROUTE
import com.tanishkej.onerep.ui.navigation.navigateToWorkOut
import com.tanishkej.onerep.ui.theme.OneRepTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun WorkoutListDetailPane(modifier: Modifier = Modifier) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator()
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }
    val nestedNavController = rememberNavController()
    fun onWorkoutClickShowDetail(workoutId: String) {
        nestedNavController.navigateToWorkOut(workoutId) {
            //popUpTo(DETAIL_PANE_NAVHOST_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }
    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            WorkoutRoute(
                showBackButton = false,
                onBackClick = listDetailNavigator::navigateBack,
                onWorkoutClick = ::onWorkoutClickShowDetail,
                modifier = modifier
            )
        },
        detailPane = {
            NavHost(
                navController = nestedNavController,
                startDestination = WORKOUT_ROUTE,
                route = "DETAIL_PANE_NAVHOST_ROUTE",
            ) {
                composable(route = WORKOUT_ROUTE) {
                    //WorkoutDetailPlaceholder()
                    Text(text = "Detail")
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OneRepTheme {
        WorkoutListDetailPane()
    }
}
