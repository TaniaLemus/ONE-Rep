package com.tanishkej.onerep.data.repository

import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.datasource.WorkoutDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineWorkoutRepository @Inject constructor(
    private val workoutDataSource: WorkoutDataSource
) : WorkoutRepository {
    override suspend fun getWorkouts(): Flow<List<Workout>> {
        return workoutDataSource.getWorkouts()
    }

    //We could scalate this app to Sync with a NetworkRepository and get the data from an API
    //Or any health service, like google health, google fit, fitbit etc...

}