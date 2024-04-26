package com.tanishkej.onerep.ui.components.graphs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun OneRepGraph(
    data: List<Entry>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        factory = { context ->
            val chart = LineChart(context)
            val dataSet = LineDataSet(data, "")
            chart.data = LineData(dataSet)
            chart.xAxis.valueFormatter = DateValueFormatter()
            chart.xAxis.setLabelCount(5, true)
            chart.axisLeft.valueFormatter = LbsValueFormatter()
            chart.axisLeft.setLabelCount(4, true)
            chart.axisRight.setDrawLabels(false)
            chart.legend.isEnabled = false
            chart.description = Description().apply {
                text = ""
            }
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

            chart.invalidate()
            chart
        }
    )
}