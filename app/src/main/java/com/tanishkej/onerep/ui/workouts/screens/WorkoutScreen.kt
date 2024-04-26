package com.tanishkej.onerep.ui.workouts.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tanishkej.onerep.R
import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.util.WorkoutUtil.getGraphEntries
import com.tanishkej.onerep.ui.components.OneRepGraph
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
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
                val graphEntries = remember {
                    groupedWorkout.workouts.getGraphEntries()
                }
                Column(
                    modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        WorkoutCard(
                            onWorkoutClick = onWorkoutClick,
                            groupedWorkout,
                            modifier = modifier
                                .fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        OneRepGraph(graphEntries)
                    }
                }
            }
        }
    }
}
