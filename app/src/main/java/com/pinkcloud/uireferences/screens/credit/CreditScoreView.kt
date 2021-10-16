package com.pinkcloud.uireferences.screens.credit

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.pinkcloud.uireferences.R
import kotlinx.coroutines.*
import kotlin.math.cos
import kotlin.math.log10
import kotlin.math.sin

class CreditScoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var score = 0
        set(value) {
            field = value
            startAnimation()
        }

    private var radius = 0f
    private var smallRadius = 0f
    private var cx = 0f
    private var cy = 0f
    private val arcWidth = 15f

    private val gradientColors =
        intArrayOf(context.getColor(R.color.credit_color_0), context.getColor(R.color.credit_color_1))
    private val gradientPositions = floatArrayOf(150 / 360f, 360 / 360f)

    private lateinit var oval: RectF

    private var arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = arcWidth
        strokeCap = Paint.Cap.ROUND
    }
    private var grayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }
    private var stickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = arcWidth
        strokeCap = Paint.Cap.ROUND
    }
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 110.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }
    private var smallTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
        textSize = 46.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private var stickAngle = 165

    init {
        startAnimation()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (w / 2.5f)
        smallRadius = (w / 30f)

        cx = (w / 2).toFloat()
        cy = 0 + radius

        oval = RectF(cx - radius, 0f + arcWidth, cx + radius, radius * 2 + arcWidth)

        arcPaint.shader = SweepGradient(cx, cy, gradientColors, gradientPositions).apply {
            val matrix = Matrix().apply {
                setRotate(15f)
            }
            setLocalMatrix(matrix)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.run {
            drawArc(oval, 165f, 210f, false, arcPaint)
            drawCircle(cx, cy, smallRadius, grayPaint)

            val sx = cx + radius * cos(stickAngle.toFloat() * Math.PI.toFloat() / 180f) * 0.7f
            val sy = cy + radius * sin(stickAngle.toFloat() * Math.PI.toFloat() / 180f) * 0.7f
            stickPaint.color = getColorFromGradient((stickAngle - 165)/210f)
            drawLine(sx, sy, cx, cy, stickPaint)

            drawText("$score", cx-10f, cy + radius*2/3, textPaint)
            drawText("점", cx+25f*(log10(score.toFloat() + 1).toInt() + 2), cy + radius*2/3, smallTextPaint)
        }
    }

    private fun startAnimation() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(200)
            val animator = ValueAnimator.ofInt(165, (165 + (score/1000f)*210).toInt()).apply {
                duration = 800
                interpolator = LinearInterpolator()
                addUpdateListener {
                    stickAngle = it.animatedValue as Int
                    invalidate()
                }
            }
            animator.start()
        }
    }

    private fun getColorFromGradient(degree: Float): Int {
        val red = 185 - ((185 - 21)*degree)
        val green = 43 + ((101 - 43)*degree)
        val blue = 39 + ((192 - 39)*degree)
        return Color.rgb(red.toInt(), green.toInt(), blue.toInt())
    }
}