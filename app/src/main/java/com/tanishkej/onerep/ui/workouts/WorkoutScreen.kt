package com.tanishkej.onerep.ui.workouts

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tanishkej.onerep.ui.components.OneRepLoadingWheel
import com.tanishkej.onerep.ui.workouts.states.WorkoutUiState
import com.tanishkej.onerep.R.string
import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.ui.workouts.cards.WorkoutCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.unit.dp

@Composable
internal fun WorkoutRoute(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (Int) -> Unit,
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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    workoutUiState: WorkoutUiState,
    modifier: Modifier,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onWorkoutClick: (Int) -> Unit
) {
    val state = rememberLazyListState()
    val context = LocalContext.current

    Box(
        modifier = modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text("One Rep & Charts") }
                )
            },
            content = {
                LazyColumn(
                    state = state,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                        .padding(it),
                ) {
                    item {
                        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
                    }
                    when (workoutUiState) {
                        WorkoutUiState.Loading -> item {
                            OneRepLoadingWheel(
                                modifier = modifier,
                                contentDesc = stringResource(id = string.feature_workouts_loading),
                            )
                        }

                        WorkoutUiState.Error -> Toast.makeText(
                            context,
                            string.feature_workouts_error,
                            Toast.LENGTH_LONG
                        ).show()

                        is WorkoutUiState.Success -> {
                            workoutCardItems(workoutUiState.workouts, onWorkoutClick)
                        }
                    }
                    item {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                    }
                }
            }
        )
    }
}

fun LazyListScope.workoutCardItems(
    items: List<Workout>,
    onWorkoutClick: (Int) -> Unit,
    itemModifier: Modifier = Modifier
) = items(
    items = items,
    key = { it.id },
    itemContent = { workoutContent ->
        WorkoutCard(
            onWorkoutClick = onWorkoutClick,
            workoutContent,
            modifier = itemModifier
        )
    },
)
