package com.tanishkej.onerep.ui.workouts.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tanishkej.onerep.ui.workouts.states.WorkoutUiState
import androidx.compose.material3.Text
import com.tanishkej.onerep.ui.workouts.viewmodels.WorkoutViewModel

@Composable
internal fun WorkoutRoute(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val workoutsUiState: WorkoutUiState by viewModel.workoutUiState.collectAsStateWithLifecycle()

    WorkoutScreen(
        workoutUiState = workoutsUiState,
        modifier = modifier.testTag("workout:${viewModel.workoutId}"),
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        onWorkoutClick = onWorkoutClick,
        viewModel
    )
}

@Composable
fun WorkoutScreen(
    workoutUiState: WorkoutUiState,
    modifier: Modifier,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    viewModel: WorkoutViewModel
) {

    Box(
        modifier = modifier,
    ) {
        Text(text = "Hi:${viewModel.workoutId}")
    }
}
