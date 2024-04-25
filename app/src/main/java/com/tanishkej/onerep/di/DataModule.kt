package com.tanishkej.onerep.di

import com.tanishkej.onerep.data.repository.OfflineWorkoutRepository
import com.tanishkej.onerep.data.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsWorkoutRepository(
        workoutRepository: OfflineWorkoutRepository,
    ): WorkoutRepository
}