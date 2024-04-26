package com.tanishkej.onerep.ui.workouts.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanishkej.onerep.data.repository.WorkoutRepository
import com.tanishkej.onerep.data.util.Result
import com.tanishkej.onerep.data.util.asResult
import com.tanishkej.onerep.ui.workouts.states.WorkoutListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(
    workoutRepository: WorkoutRepository
) : ViewModel() {

    val workoutUiState: StateFlow<WorkoutListUiState> = workoutsUiState(
        workoutRepository
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(10000),
        initialValue = WorkoutListUiState.Loading
    )

    private fun workoutsUiState(
        workoutRepository: WorkoutRepository
    ): Flow<WorkoutListUiState> {
        return workoutRepository.getWorkouts().asResult().map { workouts ->
            when (workouts) {
                is Result.Success -> WorkoutListUiState.Success(workouts.data)
                is Result.Loading -> WorkoutListUiState.Loading
                is Result.Error -> WorkoutListUiState.Error
            }
        }
    }
}