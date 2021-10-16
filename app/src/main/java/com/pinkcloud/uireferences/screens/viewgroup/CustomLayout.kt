package com.pinkcloud.uireferences.screens.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import com.pinkcloud.uireferences.R

class CustomLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }
}

class LayoutParams(val c: Context, val attrs: AttributeSet):
    ViewGroup.MarginLayoutParams(c, attrs) {

    var gravity = Gravity.TOP or Gravity.START

    

    init {
        val typedArray = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP)

    }
}
