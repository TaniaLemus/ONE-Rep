package com.tanishkej.onerep.data.repository

import com.tanishkej.onerep.data.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun getWorkouts(): Flow<List<Workout>>
}