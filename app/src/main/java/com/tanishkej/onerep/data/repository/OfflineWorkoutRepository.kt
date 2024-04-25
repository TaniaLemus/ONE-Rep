package com.tanishkej.onerep.data.repository

import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.datasource.WorkoutDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineWorkoutRepository @Inject constructor(
    private val workoutDataSource: WorkoutDataSource
) : WorkoutRepository {

    override fun getWorkouts(): Flow<List<Workout>> = workoutDataSource.getWorkouts()

    //We could scalate this app to Sync with a NetworkRepository and get the data from an API
    //Or any health service, like google health, google fit, fitbit etc...

}