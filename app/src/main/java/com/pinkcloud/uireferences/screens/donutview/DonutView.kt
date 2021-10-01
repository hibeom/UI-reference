package com.pinkcloud.uireferences.screens.donutview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (w/2).toFloat()
        innerRadius = ((h/4).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        val cx = (width/2).toFloat()
        val cy = (height/2).toFloat()
        canvas.drawCircle(cx, cy, radius, paint)
        canvas.drawCircle(cx, cy, innerRadius, innerPaint)
    }
}