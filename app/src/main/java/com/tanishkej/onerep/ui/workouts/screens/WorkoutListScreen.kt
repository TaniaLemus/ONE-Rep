package com.tanishkej.onerep.ui.workouts.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tanishkej.onerep.R
import com.tanishkej.onerep.data.model.WorkoutGroups
import com.tanishkej.onerep.ui.components.OneRepLoadingWheel
import com.tanishkej.onerep.ui.workouts.cards.WorkoutCard
import com.tanishkej.onerep.ui.workouts.states.WorkoutListUiState
import com.tanishkej.onerep.ui.workouts.viewmodels.WorkoutListViewModel

@Composable
internal fun WorkoutListRoute(
    onWorkoutClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutListViewModel = hiltViewModel(),
) {
    val workoutsUiState: WorkoutListUiState by viewModel.workoutUiState.collectAsStateWithLifecycle()

    WorkoutListScreen(
        workoutUiState = workoutsUiState,
        modifier = modifier.testTag("workoutList"),
        onWorkoutClick = onWorkoutClick,
    )
}

@Composable
fun WorkoutListScreen(
    workoutUiState: WorkoutListUiState,
    modifier: Modifier,
    onWorkoutClick: (String) -> Unit
) {
    val state = rememberLazyListState()
    val context = LocalContext.current

    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }
            when (workoutUiState) {
                WorkoutListUiState.Loading -> item {
                    OneRepLoadingWheel(
                        modifier = modifier,
                        contentDesc = stringResource(id = R.string.feature_workouts_loading),
                    )
                }

                WorkoutListUiState.Error -> Toast.makeText(
                    context,
                    R.string.feature_workouts_error,
                    Toast.LENGTH_LONG
                ).show()

                is WorkoutListUiState.Success -> {
                    workoutCardItems(workoutUiState.workoutGroups, onWorkoutClick)
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

fun LazyListScope.workoutCardItems(
    items: List<WorkoutGroups>,
    onWorkoutClick: (String) -> Unit,
    itemModifier: Modifier = Modifier
) {
    items(
        items = items,
        key = { it.workoutType },
        itemContent = { groupedWorkout ->
            WorkoutCard(
                onWorkoutClick = onWorkoutClick,
                groupedWorkout,
                modifier = itemModifier
            )
        }
    )
}

