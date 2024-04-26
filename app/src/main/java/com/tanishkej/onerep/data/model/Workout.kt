package com.tanishkej.onerep.data.model

import java.util.Date

data class Workout(
    val id: Int,
    val date: Date,
    //We could use a enum here, but since I'm not sure you will include more types, I'll leave it as String.
    val workoutType: String,
    val reps: Int,
    val weightInLBS: Int
)

fun List<Workout>.getGroupedWorkOuts(): List<WorkoutGroups> {
    val listOfGroupedWorkouts = mutableListOf<WorkoutGroups>()
    this.groupBy { workout -> workout.workoutType }.forEach { (type, workouts) ->
        listOfGroupedWorkouts.add(
            WorkoutGroups(
                maxWeightInLBS = workouts.maxOf { workout -> workout.weightInLBS },
                workoutType = type,
                workouts = workouts
            )
        )
    }
    return listOfGroupedWorkouts
}

data class WorkoutGroups(
    val maxWeightInLBS: Int,
    val workoutType: String,
    val workouts: List<Workout>
)
