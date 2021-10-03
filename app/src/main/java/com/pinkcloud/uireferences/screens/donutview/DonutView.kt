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

    var missionItems = listOf<MissionItem>()
        set(items) {
            field = items
            for (item in items) {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    style = Paint.Style.FILL
                    color = item.color
                }
                itemPaints.put(item.missionId, paint)
            }
            invalidate()
        }
    private var itemPaints = mutableMapOf<Int, Paint>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (w/2).toFloat()
        innerRadius = ((h/4).toFloat())

        oval = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        // cx is based on DonutView's left, that means cx should not be (width/2).toFloat() + left
        val cx = (width/2).toFloat()
        val cy = (height/2).toFloat()

        canvas.run {
            drawCircle(cx, cy, radius, paint)

            val totalAmount = getTotalAmount(missionItems)
            var lastAngle = 0f
            for (missionItem in missionItems) {
                val ratioAngle = (missionItem.amount.toFloat() / totalAmount)*360f
                val paint = itemPaints.get(missionItem.missionId)
                drawArc(oval, lastAngle, ratioAngle, true, paint!!)
                lastAngle += ratioAngle
            }

            drawCircle(cx, cy, innerRadius, innerPaint)
        }
    }

    private fun getTotalAmount(missionItems: List<MissionItem>): Int {
        var sum = 0
        for (mission in missionItems) {
            sum += mission.amount
        }
        return sum
    }
}