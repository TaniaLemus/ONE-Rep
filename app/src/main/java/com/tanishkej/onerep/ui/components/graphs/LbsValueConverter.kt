package com.tanishkej.onerep.ui.components.graphs

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class LbsValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return "${value.roundToInt()} lbs"
    }
}