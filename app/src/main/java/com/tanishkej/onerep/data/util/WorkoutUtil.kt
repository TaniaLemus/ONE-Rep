package com.tanishkej.onerep.data.util

import android.util.Log
import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.model.WorkoutGroups
import kotlin.math.roundToInt

private const val TAG = "WorkoutUtil"

object WorkoutUtil {

    fun List<Workout>.getGroupedWorkOuts(): List<WorkoutGroups> {
        val listOfGroupedWorkouts = mutableListOf<WorkoutGroups>()
        this.groupBy { workout -> workout.workoutType }.forEach { (type, workouts) ->
            listOfGroupedWorkouts.add(
                WorkoutGroups(
                    maxOneRep = workouts.maxOf { workout -> workout.oneRep },
                    workoutType = type,
                    workouts = workouts
                )
            )
        }
        return listOfGroupedWorkouts
    }

    /***
     * Will calculate max One rep for this workout.
     * Will return 0, in case of an error.
     * Also it will round it to Int with the default rules, >.5=UP, <.5=Down
     */
    fun Workout.getOneRep(): Int {
        return try {
            (weightInLBS / (1.0278 - 0.0278 * reps)).roundToInt()
        } catch (ex: Exception) {
            Log.e(
                TAG,
                "We cannot calculate the one rep with the following values: weightInLBS: $weightInLBS, reps: $reps",
                ex
            )
            return 0
        }
    }
}