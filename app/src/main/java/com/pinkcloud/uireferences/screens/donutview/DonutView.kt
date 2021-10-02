package com.pinkcloud.uireferences.screens.donutview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.pinkcloud.uireferences.R
import com.pinkcloud.uireferences.utils.getColorFromAttr

class DonutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private var innerRadius = 0.0f

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }
    private var innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColorFromAttr(R.attr.colorPrimary)
    }
    private lateinit var oval: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (w/2).toFloat()
        innerRadius = ((h/4).toFloat())

        Log.d("devlog", "left: $left, width: $width, right: $right, top: $top")
        oval = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("devlog", "measuredWidth: $measuredWidth")
        // cx is based on DonutView's left, that means cx should not be (width/2).toFloat() + left
        val cx = (width/2).toFloat()
        val cy = (height/2).toFloat()
        canvas.drawCircle(cx, cy, radius, paint)

        val paint0 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.BLUE
        }
        canvas.drawArc(oval, 0f, 90f, true, paint0)
        canvas.drawCircle(cx, cy, innerRadius, innerPaint)
    }
}