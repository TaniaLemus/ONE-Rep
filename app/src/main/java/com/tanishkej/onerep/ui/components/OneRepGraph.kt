package com.tanishkej.onerep.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun OneRepGraph(
    data :List<Entry>,
    modifier: Modifier = Modifier
){
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
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

            chart.invalidate()
            chart
        }
    )
}

class LbsValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return "${value.roundToInt()} lbs"
    }
}


class DateValueFormatter : ValueFormatter() {
    private var formatter: Format = SimpleDateFormat("MMM dd", Locale.US)
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return formatter.format(Date(value.toLong()))
    }
}