package com.tanishkej.onerep.ui.workouts.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tanishkej.onerep.ui.workouts.states.WorkoutUiState
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.tanishkej.onerep.R
import com.tanishkej.onerep.ui.components.OneRepLoadingWheel
import com.tanishkej.onerep.ui.workouts.cards.WorkoutCard
import com.tanishkej.onerep.ui.workouts.states.WorkoutListUiState
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
        workoutId = viewModel.workoutId
    )
}

@Composable
fun WorkoutScreen(
    workoutUiState: WorkoutUiState,
    modifier: Modifier,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    workoutId: String
) {

    val context = LocalContext.current
    Box(
        modifier = modifier,
    ) {

        when (workoutUiState) {
            WorkoutUiState.Loading -> {
                OneRepLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.feature_workout_detail_loading),
                )
            }
            WorkoutUiState.Error -> {
                Toast.makeText(
                    context,
                    R.string.feature_workouts_error,
                    Toast.LENGTH_LONG
                ).show()
            }
            is WorkoutUiState.Success -> {
                val groupedWorkout = remember {
                    workoutUiState.workoutGroups.firstOrNull { workout -> workout.workoutType == workoutId }
                } ?: return@Box

                WorkoutCard(
                    onWorkoutClick = onWorkoutClick,
                    groupedWorkout,
                    modifier = modifier
                )
            }
        }
    }
}
