package com.larina.mymovie.view.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RatingDonutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private var progress: Int = 0
        private set

    private val paint = Paint()
    private val textPaint = Paint()

    init {
        // Настройка краски для круга
        paint.color = Color.BLUE // Цвет круга
        paint.style = Paint.Style.FILL

        // Настройка краски для текста
        textPaint.color = Color.WHITE // Цвет текста
        textPaint.textSize = 50f // Размер текста
        textPaint.textAlign = Paint.Align.CENTER // Выравнивание текста по центру
    }

    fun setProgress(progress: Int) {
        this.progress = progress.coerceIn(0, 100) // Ограничиваем прогресс от 0 до 100
        invalidate() // Перерисовать вид при изменении прогресса
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = width / 2f
        val centerX = width / 2f
        val centerY = height / 2f

        // Рисуем круг
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Рисуем прогресс
        paint.color = Color.LTGRAY // Цвет фона для прогресса
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            -90f,
            (progress * 360 / 100).toFloat(),
            true,
            paint
        )

        // Рисуем текст с оценкой
        paint.color = Color.BLUE // Цвет текста
        val text = "$progress%"
        canvas.drawText(text, centerX, centerY + (textPaint.textSize / 4), textPaint)
    }
}
