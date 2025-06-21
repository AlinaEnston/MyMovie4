package com.larina.mymovie

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class RatingDonutView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private val oval = RectF()
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var stroke = 10f

    var progress: Int = 50
        set(value) {
            field = value.coerceIn(0, 100)
            updatePaintColors()
            invalidate()
        }

    private var scaleSize = 60f

    private var strokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var digitPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val a = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RatingDonutView,
            0,
            0
        )
        try {
            stroke = a.getFloat(R.styleable.RatingDonutView_stroke, stroke)
            progress = a.getInt(R.styleable.RatingDonutView_progress, progress)
        } finally {
            a.recycle()
        }
        initPaint()
    }

    private fun initPaint() {
        strokePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            color = getPaintColor(progress)
            strokeCap = Paint.Cap.ROUND
        }
        digitPaint.apply {
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
            setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
            textSize = scaleSize
            typeface = Typeface.SANS_SERIF
            textAlign = Paint.Align.CENTER
            color = Color.GREEN  // Set text color to green
        }
        circlePaint.apply {
            style = Paint.Style.FILL
            color = Color.BLACK  // Set background color to black
        }
    }


    private fun getPaintColor(progress: Int): Int = when (progress) {
        in 0..25 -> Color.parseColor("#4B0082")
        in 26..50 -> Color.parseColor("#9400D3")
        in 51..75 -> Color.parseColor("#9ACD32")
        else -> Color.parseColor("#00BFFF")
    }

    private fun updatePaintColors() {
        val color = getPaintColor(progress)
        strokePaint.color = color
    }

    private fun convertProgressToDegrees(progress: Int): Float = progress * 3.6f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = if (w > h) h / 2f else w / 2f
        centerX = w / 2f
        centerY = h / 2f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val chosenWidth = chooseDimension(widthMode, widthSize)
        val chosenHeight = chooseDimension(heightMode, heightSize)

        val minSide = Math.min(chosenWidth, chosenHeight)
        centerX = minSide / 2f
        centerY = minSide / 2f

        setMeasuredDimension(minSide, minSide)
    }

    private fun chooseDimension(mode: Int, size: Int): Int {
        return when (mode) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> size
            else -> 300
        }
    }

    private fun drawRating(canvas: Canvas) {
        val scale = radius * 0.8f
        canvas.save()
        canvas.translate(centerX, centerY)
        oval.set(-scale, -scale, scale, scale)
        canvas.drawCircle(0f, 0f, radius, circlePaint)
        canvas.drawArc(oval, -90f, convertProgressToDegrees(progress), false, strokePaint)
        canvas.restore()
    }

    private fun drawText(canvas: Canvas) {
        val message = String.format("%.1f", progress / 10f)
        val fm = digitPaint.fontMetrics
        val textHeight = fm.descent - fm.ascent
        val textVerticalOffset = textHeight / 2 - fm.descent

        canvas.save()
        canvas.translate(centerX, centerY)
        // Draw the text centered horizontally and vertically
        canvas.drawText(message, 0f, textVerticalOffset, digitPaint)
        canvas.restore()
    }

    override fun onDraw(canvas: Canvas) {
        drawRating(canvas)
        drawText(canvas)
    }
}