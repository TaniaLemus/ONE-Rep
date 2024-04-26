package com.tanishkej.onerep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.tanishkej.onerep.ui.navigation.workoutScreen
import com.tanishkej.onerep.ui.workouts.WorkoutRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneRepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Workouts()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Workouts(modifier: Modifier = Modifier) {
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
                workoutScreen(
                    showBackButton = false,
                    onBackClick = listDetailNavigator::navigateBack,
                    onWorkoutClick = ::onWorkoutClickShowDetail
                )
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
        Workouts()
    }
}