package com.tanishkej.onerep.data.util

import android.util.Log
import com.tanishkej.onerep.OneRepApplication
import com.tanishkej.onerep.data.model.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

private const val TAG = "WorkoutFileReader"
private const val WORKOUT_FILE_NAME = "workoutData.txt"

class WorkoutFileReader @Inject constructor(private val oneRepApplication: OneRepApplication) {

    suspend fun getWorkouts(): List<Workout>? {
        return suspendCancellableCoroutine { coroutine ->
            val listOfWorkouts = mutableListOf<Workout>()
            try {
                oneRepApplication.assets.open(WORKOUT_FILE_NAME).bufferedReader()
                    .use { fileContent ->
                        fileContent.forEachLine { lineContent ->
                            getWorkout(lineContent)?.let { workout ->
                                listOfWorkouts.add(workout)
                            }
                        }
                    }
                coroutine.resume(listOfWorkouts)
            } catch (ex: Exception) {
                Log.i(TAG, "The file does not exists or the format or cannot be read.")
                coroutine.resume(null)
            }
        }
    }

    private fun getWorkout(line: String): Workout? {
        var date: Date? = null
        var workoutType = ""
        var reps: Int = -1
        var weightInLBS: Int = -1
        line.split(',').forEachIndexed { index, value ->
            when (index) {
                0 -> date = convertToDate(value)
                1 -> workoutType = value
                2 -> reps = convertToInt(value)
                3 -> weightInLBS = convertToInt(value)
                else -> Log.i(
                    TAG,
                    "There is an unexpected value on the file on this line: $line, please review its content."
                )
            }
        }
        return if (date != null &&
            workoutType.isNotEmpty() &&
            reps > -1 &&
            weightInLBS > -1
        ) {
            Workout(date!!, workoutType, reps, weightInLBS)
        } else {
            Log.i(
                TAG,
                "There is a problem with one of the values, the workout will not be included"
            )
            null
        }
    }

    private fun convertToInt(valueString: String): Int {
        return try {
            valueString.toInt()
        } catch (ex: Exception) {
            Log.i(TAG, "The value $valueString cannot be parse to a Int obj.")
            -1
        }
    }

    private fun convertToDate(dateString: String): Date? {
        return try {
            val format: DateFormat = SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH)
            format.parse(dateString)
        } catch (ex: Exception) {
            Log.i(
                TAG,
                "The date $dateString cannot be parse to a date obj, the format should be: MMM dd yyyy"
            )
            null
        }
    }
}