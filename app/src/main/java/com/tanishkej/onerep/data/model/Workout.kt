package com.tanishkej.onerep.data.model

import java.util.Date

data class Workout(
    val date: Date,
    //We could use a enum here, but since I'm not sure you will include more types, I'll leave it as String.
    val workoutType: String,
    val reps: Int,
    val weightInLBS: Int
)
