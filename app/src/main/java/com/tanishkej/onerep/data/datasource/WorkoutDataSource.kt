package com.tanishkej.onerep.data.datasource

import android.util.Log
import com.tanishkej.onerep.OneRepApplication
import com.tanishkej.onerep.data.model.Workout
import com.tanishkej.onerep.data.model.WorkoutGroups
import com.tanishkej.onerep.data.util.WorkoutUtil.getGroupedWorkOuts
import com.tanishkej.onerep.data.util.WorkoutUtil.getOneRep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "WorkoutFileReader"
private const val WORKOUT_FILE_NAME = "workoutData.txt"

class WorkoutDataSource @Inject constructor(
    private val oneRepApplication: OneRepApplication
) {
    /***
     * Since the asset is static store the data here, so we do not need to reload it all the time.
     * There is a better way to do this having room whit ADO.
     */
    companion object {
        private var groupedWorkOutsCache: List<WorkoutGroups> = listOf()
    }

    fun getWorkouts(): Flow<List<WorkoutGroups>> {
        return flow {
            if (groupedWorkOutsCache.isNotEmpty()) {
                emit(groupedWorkOutsCache)
            } else {
                val listOfWorkouts = mutableListOf<Workout>()
                try {
                    oneRepApplication.assets.open(WORKOUT_FILE_NAME).bufferedReader()
                        .use { fileContent ->
                            var id = 1
                            fileContent.forEachLine { lineContent ->
                                getWorkout(id, lineContent)?.let { workout ->
                                    listOfWorkouts.add(workout)
                                }
                                id++
                            }
                        }
                    groupedWorkOutsCache = listOfWorkouts.getGroupedWorkOuts()
                    emit(groupedWorkOutsCache)
                } catch (ex: Exception) {
                    Log.i(TAG, "The file does not exists or the format or cannot be read.")
                    emit(emptyList())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getWorkout(id: Int, line: String): Workout? {
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
            Workout(id, date!!, workoutType, reps, weightInLBS).apply {
                oneRep = getOneRep()
            }
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
            Log.i(TAG, "The value $valueString cannot be parse to an Int obj.")
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