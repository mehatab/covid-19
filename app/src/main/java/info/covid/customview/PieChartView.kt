package info.covid.customview
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import info.covid.R


class PieChartView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var slicePaint: Paint = Paint()
    private var centerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sliceColors: IntArray = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
    private lateinit var rectF: RectF
    private var dataPoints: FloatArray = floatArrayOf()

    init {
        slicePaint.isAntiAlias = true
        slicePaint.isDither = true
        slicePaint.style = Paint.Style.FILL

        centerPaint.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        centerPaint.style = Paint.Style.FILL
    }

    private fun scale(): FloatArray {
        var scaledValues = FloatArray(dataPoints.size)
        for (i in dataPoints.indices) {
            scaledValues.fill((dataPoints[i] / getTotal()) * 360, i, dataPoints.size)
        }
        return scaledValues
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val startTop = 0F
        val startLeft = 0F
        val endBottom = width.toFloat()
        val endRight = endBottom

        rectF = RectF(startLeft, startTop, endRight, endBottom)

        val scaledValues = scale()
        var sliceStartPoint = 0F

        for (i in scaledValues.indices) {
            slicePaint.color = sliceColors[i]
            canvas!!.drawArc(rectF, sliceStartPoint, scaledValues[i], true, slicePaint)
            sliceStartPoint += scaledValues[i]
        }


        val centerX = (measuredWidth / 2).toFloat()
        val centerY = (measuredHeight / 2).toFloat()
        val radius = Math.min(centerX, centerY)

        canvas!!.drawCircle(centerX, centerY, radius - 10, centerPaint)

    }

    fun getTotal(): Float = dataPoints.sum()

    fun setDataPoints(data: FloatArray) {
        dataPoints = data
        invalidateAndRequestLayout()
    }

    fun setCenterColor(colorId: Int) {
        centerPaint.color = ContextCompat.getColor(context, colorId)
        invalidateAndRequestLayout()
    }

    fun setSliceColor(colors: IntArray) {
        sliceColors = colors
        invalidateAndRequestLayout()
    }

    private fun invalidateAndRequestLayout() {
        invalidate()
        requestLayout()
    }
}