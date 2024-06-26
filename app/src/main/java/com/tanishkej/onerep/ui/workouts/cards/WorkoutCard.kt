package com.tanishkej.onerep.ui.workouts.cards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tanishkej.onerep.R.string
import com.tanishkej.onerep.data.model.WorkoutGroups
import com.tanishkej.onerep.ui.theme.Purple80

@Composable
fun WorkoutCard(
    onWorkoutClick: (String) -> Unit,
    workout: WorkoutGroups,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(16.dp))
    Card(
        onClick = {
            onWorkoutClick(workout.workoutType)
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .border(
                width = 1.dp,
                color = Purple80,
                shape = RoundedCornerShape(
                    size = 32.dp,
                )
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp,
            )
    ) {
        Row {
            Column {
                Text(
                    workout.workoutType,
                    modifier = Modifier.fillMaxWidth((.8f)),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    stringResource(id = string.feature_workouts_card_rm_recorded),
                    modifier = Modifier.fillMaxWidth((.8f)),
                )
            }
            Column {
                Text(
                    "${workout.maxOneRep}",
                    modifier = Modifier.fillMaxWidth((.8f)),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    stringResource(id = string.feature_workouts_card_lbs),
                    modifier = Modifier.fillMaxWidth((.8f)),
                )
            }
        }
    }
}