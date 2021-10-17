package com.pinkcloud.uireferences.screens.rating

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.pinkcloud.uireferences.R

class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var rating = 0
    var maxRating = 5
    var starColor = Color.YELLOW

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        context.withStyledAttributes(attrs, R.styleable.RatingView) {
            maxRating = getInt(R.styleable.RatingView_maxRating, 5)
            rating = getInt(R.styleable.RatingView_rating, 0)
            starColor = getInt(R.styleable.RatingView_android_color, Color.YELLOW)
        }

        for (i in 0 until maxRating) {
            val child = StarView(context)
            child.starColor = starColor
            if (i < rating) {
                child.checked = true
            }
            child.setOnClickListener {
                rating = i + 1
                Log.d("devlog", "rating:x $rating")
                updateChildren()
            }
            addView(child)
        }
    }

    private fun updateChildren() {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i) as StarView
            child.checked = i < rating
        }
    }

//    override fun onDraw(canvas: Canvas?) {
//        Log.d("devlog", "rating: $rating")
//        val count = childCount
//        for (i in 0 until count) {
//            val child = getChildAt(i) as StarView
//            child.checked = i < rating
//        }
//    }
}