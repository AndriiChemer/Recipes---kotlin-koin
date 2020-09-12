package com.artatech.inkbook.recipes.core.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.artatech.inkbook.recipes.R

class CircleHalfView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var pain: Paint = Paint().apply {
        color = ContextCompat.getColor(getContext(), R.color.backgroundColor)
        strokeWidth = 10f
        style = Paint.Style.FILL
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {


        val curvePath = Path()
        curvePath.moveTo(0f, 0f)
        curvePath.quadTo((width / 2).toFloat(), 150f, width.toFloat(), 0f)
        curvePath.lineTo( width.toFloat(),  height.toFloat())
        curvePath.lineTo( 0f,  height.toFloat())
        curvePath.lineTo( 0f,  0f)

        canvas?.drawPath(curvePath, pain)

        super.onDraw(canvas)
    }
}