package info.covid.uicomponents.customview.sectionbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import info.covid.uicomponents.R

class SectionProgressBar @JvmOverloads constructor(
    context: Context,
    @Nullable val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var pogress = Progress()

    private val borderPathWidth by lazy {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, height.toFloat(), resources.displayMetrics
        )
    }

    init {
        paint.apply {
            color = Color.RED
            strokeCap = Paint.Cap.ROUND
            strokeWidth = borderPathWidth
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            color = ContextCompat.getColor(context, R.color.confirmed)
        }

        canvas?.drawLine(
            10f,
            height.toFloat() / 2f,
            width * pogress.active,
            height.toFloat() / 2,
            paint
        )

        paint.apply {
            color = ContextCompat.getColor(context, R.color.recovered)
        }

        canvas?.drawLine(
            width * pogress.active,
            height.toFloat() / 2f,
            width * pogress.recovered.plus(pogress.active),
            height.toFloat() / 2,
            paint
        )

        paint.apply {
            color = ContextCompat.getColor(context, R.color.deaths)
        }

        canvas?.drawLine(
            width * pogress.recovered.plus(pogress.active),
            height.toFloat() / 2f,
            width * (1f),
            height.toFloat() / 2,
            paint
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.strokeWidth = height.toFloat() / 1.5f
    }


    fun setProgress(active: Float, recovered: Float, deaths: Float) {
        pogress = Progress(active, recovered, deaths)
        invalidate()
    }

    data class Progress(
        var active: Float = 0f,
        var recovered: Float = 0f,
        var deaths: Float = 0f
    )
}