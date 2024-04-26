package com.tanishkej.onerep.ui.components.graphs

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateValueFormatter : ValueFormatter() {
    private var formatter: Format = SimpleDateFormat("MMM dd", Locale.US)
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return formatter.format(Date(value.toLong()))
    }
}