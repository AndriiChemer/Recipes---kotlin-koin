package com.artatech.inkbook.recipes.customcategory

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.artatech.inkbook.recipes.R
import kotlin.math.cos
import kotlin.math.sin

class CustomCategoryGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val randomCircles = arrayListOf<CircleModel>()
    private val randomCircleCount = 11

    private var cx: Float = 0f
    private var cy: Float = 0f

    init {
        with(circlePaint) {
            style = Paint.Style.STROKE
            color = ContextCompat.getColor(context, R.color.layoutBackground)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        cx = (width / 2).toFloat()
        cy = (height / 2).toFloat()

        val innerRadius = 5f
        val outerRadius = (width / 2).toFloat()



        canvas?.drawCircle(cx, cy, innerRadius, circlePaint)
        canvas?.drawCircle(cx, cy, outerRadius, circlePaint)

        while (randomCircles.size < randomCircleCount) {
            randomCircle(canvas, innerRadius, outerRadius)
        }
    }

    private fun randomCircle(canvas: Canvas?, innerRadius: Float, outerRadius: Float) {
        val pi2 = Math.PI * 2
        val minCircle = 200 / 2
        val maxCircle = 300 / 2
        val radius = minCircle + Math.random() * (maxCircle - minCircle)
        var distanceFromCenter = innerRadius + radius + Math.random() * (outerRadius - innerRadius - radius * 2)
        val angle = Math.random() * pi2
        val x = cx + distanceFromCenter * cos(angle)
        val y = cy + distanceFromCenter * sin(angle)

        var hit = false

        randomCircles.forEach { circleModel ->
            val dx = circleModel.cx - x
            val dy = circleModel.cy - y
            val r = circleModel.radius + radius
            if (dx * dx + dy * dy <= r * r) {
                hit = true
            }
        }

        if (!hit) {
            var color = Color.RED
            randomCircles.add(CircleModel(x.toFloat(), y.toFloat(), radius.toFloat(), color))

            dwarCircle(canvas, x, y, radius, color)
        }
    }

    private fun dwarCircle(canvas: Canvas?, x: Double, y: Double, radius: Double, colorBlue: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = colorBlue
        }
        canvas?.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paint)
    }
}