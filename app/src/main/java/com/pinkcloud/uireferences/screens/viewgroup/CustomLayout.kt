package com.pinkcloud.uireferences.screens.viewgroup

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.pinkcloud.uireferences.R

class CustomLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    /** The amount of space used by children in the left gutter. */
    private var mLeftWidth: Int = 0

    /** The amount of space used by children in the right gutter. */
    private var mRightWidth: Int = 0

    /** These are used for computing child frames based on their gravity. */
    private val mTmpContainerRect = Rect()
    private val mTmpChildRect = Rect()

    /**
     * Return true if the pressed state should be delayed for children
     * or descendants of this ViewGroup.
     * Any layout manager that doesn't scroll will want this.
     * */
    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *
     * A MeasureSpec encapsulates the layout requirements passed from parent to child.
     * Each MeasureSpec represents a requirement for either the width of the height.
     * There are three possible modes for MeasureSpec
     * - UNSPECIFIED : The parent has not imposed any constraint on the child.
     * - EXACTLY : The parent has determined an exact size for the child.
     * - AT_MOST : The child can be as large as it wants up to the specified size.
     **/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount

        // These keep track of the space we are using on the left and right for
        // views positioned there; we need member variables so we can also use
        // these for layout later.
        mLeftWidth = 0; mRightWidth = 0

        // Measurement will ultimately be computing these values.
        var maxHeight = 0
        var maxWidth = 0
        var childState = 0

        // Iterate through all children, measuring them and
        // computing our dimensions from their size.
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                // Measure the child.
                // widthUsed : Extra space that has been used by the parent horizontally. (possibly by other children of the parent)
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)

                // Update our size information based on the layout params.  Children
                // that asked to be positioned on the left or right go in those gutters.
                val lp =
                    child.layoutParams as com.pinkcloud.uireferences.screens.viewgroup.LayoutParams
                if (lp.position == com.pinkcloud.uireferences.screens.viewgroup.LayoutParams.POSITION_LEFT) {
                    mLeftWidth += Math.max(
                        maxWidth,
                        child.measuredWidth + lp.leftMargin + lp.rightMargin
                    )
                } else if (lp.position == com.pinkcloud.uireferences.screens.viewgroup.LayoutParams.POSITION_RIGHT) {
                    mRightWidth += Math.max(
                        maxWidth,
                        child.measuredWidth + lp.leftMargin + lp.rightMargin
                    )
                } else {
                    maxWidth =
                        Math.max(maxWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
                }
                maxHeight =
                    Math.max(maxHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
                childState = combineMeasuredStates(childState, child.measuredState)
            }
        }

        // Total width is the maximum width of all inner children plus the gutters.
        maxWidth += mLeftWidth + mRightWidth
        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)

        // Report our final dimensions.
        setMeasuredDimension(
            resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
            resolveSizeAndState(maxHeight, heightMeasureSpec, childState)
        )
    }

    /**
     * Position all children within this layout.
     */
    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val count = childCount

        // These are the far left and right edges in which we are performing layout.
        var leftPos = paddingLeft
        var rightPos = right - left - paddingRight

        // This is the middle region inside of the gutter.
        val middleLeft = leftPos + mLeftWidth
        val middleRight = rightPos - mRightWidth

        // These are the top and bottom edges in which we are performing layout.
        val topPos = paddingTop
        val bottomPos = bottom - top - paddingBottom

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp =
                    child.layoutParams as com.pinkcloud.uireferences.screens.viewgroup.LayoutParams

                val width = child.measuredWidth
                val height = child.measuredHeight

                // Compute the frame in which we are placing this child.
                if (lp.position == com.pinkcloud.uireferences.screens.viewgroup.LayoutParams.POSITION_LEFT) {
                    mTmpContainerRect.left = leftPos + lp.leftMargin
                    mTmpContainerRect.right = leftPos + width + lp.rightMargin
                    leftPos = mTmpContainerRect.right
                } else if (lp.position == com.pinkcloud.uireferences.screens.viewgroup.LayoutParams.POSITION_RIGHT) {
                    mTmpContainerRect.right = rightPos - lp.rightMargin
                    mTmpContainerRect.left = rightPos - width - lp.leftMargin
                    rightPos = mTmpContainerRect.left
                } else {
                    mTmpContainerRect.left = middleLeft + lp.leftMargin
                    mTmpContainerRect.right = middleRight - lp.rightMargin
                }
                mTmpContainerRect.top = topPos + lp.topMargin
                mTmpContainerRect.bottom = bottomPos - lp.bottomMargin

                // Use the child's gravity and size to determine its final
                // frame within its container.
                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect)

                // Place the child.
                child.layout(
                    mTmpChildRect.left, mTmpChildRect.top,
                    mTmpChildRect.right, mTmpChildRect.bottom
                )
            }
        }
    }
}

class LayoutParams : ViewGroup.MarginLayoutParams {

    var gravity = Gravity.TOP or Gravity.START

    var position = POSITION_MIDDLE

    companion object {
        val POSITION_MIDDLE = 0
        val POSITION_LEFT = 1
        val POSITION_RIGHT = 2
    }

    constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {
        val typedArray = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP)
        gravity = typedArray.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity)
        position = typedArray.getInt(R.styleable.CustomLayoutLP_layout_position, position)
        typedArray.recycle()
    }

    constructor(width: Int, height: Int) : super(width, height)

    constructor(source: ViewGroup.LayoutParams) : super(source)
}
