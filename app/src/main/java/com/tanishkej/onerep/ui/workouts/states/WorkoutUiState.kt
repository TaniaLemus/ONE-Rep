package com.tanishkej.onerep.ui.workouts.states

import com.tanishkej.onerep.data.model.WorkoutGroups

sealed interface WorkoutUiState {
    data class Success(val workoutGroups: List<WorkoutGroups>) : WorkoutUiState
    data object Error : WorkoutUiState
    data object Loading : WorkoutUiState
}