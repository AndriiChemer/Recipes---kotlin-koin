package com.artatech.inkbook.recipes.core.ui.custom.bottomnavview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.artatech.inkbook.recipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.floor

class CustomBottomNavView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val SECTION_1 = 6f
    private val SECTION_2 = 2f
    private val SECTION_3 = 1.2f

    private val mPath = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /** the radius represent the radius of the fab button  */
    var radius: Int = 0

    // the coordinates of the first curve
    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    val  mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()

    //the coordinates of the second curve
    private var mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()

    // Navigation bar bounds (width & height)
    var mNavigationBarWidth: Int = 0
    var mNavigationBarHeight: Int = 0

    init {
        radius = 90

        try {
            val indexPosition = floor(menu.size() / 2.0).toInt()
            selectedItemId = menu.getItem(indexPosition).itemId
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        with(mPaint) {
            style = Paint.Style.FILL_AND_STROKE
            color = ContextCompat.getColor(context, R.color.layoutBackground)
        }
        setBackgroundColor(ContextCompat.getColor(context, R.color.navBarBackgroundColor))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        prepareSelected(SECTION_2)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPath.apply {
            reset()
            moveTo(0f, 0f)
            lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())

            cubicTo(
                mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat(),
                mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat(),
                mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat()
            )

            cubicTo(
                mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat(),
                mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat(),
                mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat()
            )

            lineTo(mNavigationBarWidth.toFloat(), 0f)
            lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
            lineTo(0f, mNavigationBarHeight.toFloat())
            close()
        }

        canvas?.drawPath(mPath, mPaint)
    }

    private fun prepareSelected(section: Float) {
        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        val mNavigationBarWidth = width
        val mNavigationBarHeight = height

        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set((mNavigationBarWidth / section - radius * 2 - radius / 3).toInt(), 0)
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set((mNavigationBarWidth / section).toInt(), radius + radius / 4)
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint.set((mNavigationBarWidth / section + radius * 2 + radius / 3).toInt(), 0)

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(
            mFirstCurveEndPoint.x - radius * 2 + radius,
            mFirstCurveStartPoint.y
        )
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(
            mFirstCurveEndPoint.x - radius * 2 + radius,
            mFirstCurveEndPoint.y
        )

        mSecondCurveControlPoint1.set(
            mSecondCurveStartPoint.x + radius * 2 - radius,
            mSecondCurveStartPoint.y
        )
        mSecondCurveControlPoint2.set(
            mSecondCurveEndPoint.x - (radius + radius / 4),
            mSecondCurveEndPoint.y
        )
    }

    fun loadByPosition(index: Int) {
        when (index) {
            0 -> {
                prepareSelected(SECTION_1)
            }
            1 -> {
                prepareSelected(SECTION_2)
            }
            2 -> {
                prepareSelected(SECTION_3)
            }
        }
    }
}