package com.tanishkej.onerep.data.model

data class WorkoutGroups(
    val maxOneRep: Int,
    val workoutType: String,
    val workouts: List<Workout>
)