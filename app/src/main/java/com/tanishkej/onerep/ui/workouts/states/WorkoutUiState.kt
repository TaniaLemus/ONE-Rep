package com.tanishkej.onerep.ui.workouts.states

import com.tanishkej.onerep.data.model.Workout

sealed interface WorkoutUiState {
    data class Success(val workouts: List<Workout>) : WorkoutUiState
    data object Error : WorkoutUiState
    data object Loading : WorkoutUiState
}