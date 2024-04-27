package com.tanishkej.onerep.data.util

import android.util.Log
import com.github.mikephil.charting.data.Entry
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
     * Will calculate max One rep for this workout using the Matt Brzycki Formula..
     * Will return 0, in case of an error.
     * Also it will round it to Int with the default rules, >.5=UP, <.5=Down
     */
    fun Workout.getOneRep(): Int {
        return try {
            if (reps == 0) return 0
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

    fun List<Workout>.getGraphEntries() =
        try {
            val filteredWorkoutData = this.sortedBy { it.date }
                .fold(mutableListOf<Workout>() to Int.MIN_VALUE)
                { (filtered, prevOneRep), workout ->
                    if (workout.oneRep > prevOneRep) {
                        filtered.add(workout)
                        filtered to workout.oneRep
                    } else {
                        filtered to prevOneRep
                    }
                }.first

            val noteRepeatedDays = filteredWorkoutData
                .groupBy { it.date }
                .mapValues { (_, workout) ->
                    workout.maxByOrNull { it.oneRep }!!.oneRep
                }
            noteRepeatedDays.map { (x, y) -> Entry(x.time.toFloat(), y.toFloat()) }
        } catch (ex: Exception) {
            Log.e(
                TAG,
                "Not able to generate the list of Entries for the Graph.",
                ex
            )
            listOf()
        }
}
