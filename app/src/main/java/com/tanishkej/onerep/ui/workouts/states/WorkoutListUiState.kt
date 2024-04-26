package com.tanishkej.onerep.ui.workouts.states

import com.tanishkej.onerep.data.model.WorkoutGroups
sealed interface WorkoutListUiState {
    data class Success(val workoutGroups: List<WorkoutGroups>) : WorkoutListUiState
    data object Error : WorkoutListUiState
    data object Loading : WorkoutListUiState
}