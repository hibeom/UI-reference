package com.pinkcloud.uireferences.screens.rating

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.pinkcloud.uireferences.R

class StarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var checked = false
        set(value) {
            field = value
            invalidate()
        }
    var starColor = Color.YELLOW
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.StarView) {
            checked = getBoolean(R.styleable.StarView_android_checked, false)
            starColor = getColor(R.styleable.StarView_starColor, Color.YELLOW)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        printMeasureLog(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        when (widthMode) {
            MeasureSpec.EXACTLY -> {
//                Log.d("devlog", "m w:$measuredWidth, m h: $measuredHeight")
//                Log.d("devlog", "w:$width, h: $height")
                setMeasuredDimension(widthSize,heightSize)
            }
            MeasureSpec.AT_MOST -> {
                setMeasuredDimension(66,66)
            }
            else -> {
                setMeasuredDimension(66,66)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = if(checked) {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_star_fill, null)!!.apply {
                setTint(starColor)
            }
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_star_outline, null)!!.apply {
                setTint(starColor)
            }
        }
        drawable?.let {
//            Log.d("devlog","intri width$intrinsicWidth")
//            Log.d("devlog","intri height$intrinsicHeight")

            it.setBounds(0, 0, width, height)
            it.draw(canvas)
        }
    }

    private fun printMeasureLog(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        Log.d("devlog", "width mode: $widthMode, width size: $widthSize")
        when (widthMode) {
            MeasureSpec.EXACTLY -> Log.d("devlog", "exactly")
            MeasureSpec.AT_MOST -> Log.d("devlog", "at most")
            else -> Log.d("devlog", "unspecified")
        }
    }

}