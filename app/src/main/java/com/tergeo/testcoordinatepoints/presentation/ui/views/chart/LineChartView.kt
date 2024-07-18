package com.tergeo.testcoordinatepoints.presentation.ui.views.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


// Можно было бы добавить отдельный стейт для вью и вынести различные атрибуты в хмл, но для тз не стал делать

class LineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var chartPoints: List<ChartPoint> = emptyList()
    private val path = Path()
    private val pointPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    private val linePaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    fun setPoints(chartPoints: List<ChartPoint>) {
        this.chartPoints = chartPoints
        invalidate()  // Запросить перерисовку
    }

    private fun preparePath() {
        path.reset()

        if (chartPoints.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()

        val minX = chartPoints.minOf { it.x }
        val maxX = chartPoints.maxOf { it.x }
        val minY = chartPoints.minOf { it.y }
        val maxY = chartPoints.maxOf { it.y }

        val scaleX = width / (maxX - minX)
        val scaleY = height / (maxY - minY)

        val startPoint = chartPoints.first()
        val startX = (startPoint.x - minX) * scaleX
        val startY = height - (startPoint.y - minY) * scaleY
        path.moveTo(startX, startY)

        for (i in 1 until chartPoints.size) {
            val point = chartPoints[i]
            val x = (point.x - minX) * scaleX
            val y = height - (point.y - minY) * scaleY
            path.lineTo(x, y)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (chartPoints.isEmpty()) return

        preparePath()

        val width = width.toFloat()
        val height = height.toFloat()

        val minX = chartPoints.minOf { it.x }
        val maxX = chartPoints.maxOf { it.x }
        val minY = chartPoints.minOf { it.y }
        val maxY = chartPoints.maxOf { it.y }

        val scaleX = width / (maxX - minX)
        val scaleY = height / (maxY - minY)

        for (point in chartPoints) {
            val x = (point.x - minX) * scaleX
            val y = height - (point.y - minY) * scaleY
            canvas.drawCircle(x, y, 10f, pointPaint)
        }

        canvas.drawPath(path, linePaint)
    }
}