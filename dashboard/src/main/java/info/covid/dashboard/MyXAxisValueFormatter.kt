package info.covid.dashboard

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MyXAxisValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return try {
            SimpleDateFormat("d/MM", Locale.getDefault()).format(Date(value.toLong()))
        } catch (e: Exception) {
            ""
        }
    }
}