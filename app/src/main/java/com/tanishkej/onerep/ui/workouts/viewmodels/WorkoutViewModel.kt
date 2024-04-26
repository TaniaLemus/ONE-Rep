package com.tanishkej.onerep.ui.workouts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanishkej.onerep.data.repository.WorkoutRepository
import com.tanishkej.onerep.data.util.asResult
import com.tanishkej.onerep.data.util.Result
import com.tanishkej.onerep.ui.navigation.WorkoutArgs
import com.tanishkej.onerep.ui.workouts.states.WorkoutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    workoutRepository: WorkoutRepository
) : ViewModel() {

    private val workoutArgs: WorkoutArgs = WorkoutArgs(savedStateHandle)

    /***
     * Not implemented yet, but we could use Universal Linking Nav to go straight to a Workout.
     */
    val workoutId = workoutArgs.workoutId

    val workoutUiState: StateFlow<WorkoutUiState> = workoutsUiState(
        workoutRepository
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(10000),
        initialValue = WorkoutUiState.Loading
    )

    private fun workoutsUiState(
        workoutRepository: WorkoutRepository
    ): Flow<WorkoutUiState> {
        return workoutRepository.getWorkouts().asResult().map { workouts ->
            when (workouts) {
                is Result.Success -> WorkoutUiState.Success(workouts.data)
                is Result.Loading -> WorkoutUiState.Loading
                is Result.Error -> WorkoutUiState.Error
            }
        }
    }
}