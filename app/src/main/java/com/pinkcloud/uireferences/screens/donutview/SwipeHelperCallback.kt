package com.pinkcloud.uireferences.screens.donutview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pinkcloud.uireferences.R
import kotlin.math.max
import kotlin.math.min

class SwipeHelperCallback : ItemTouchHelper.Callback() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.d("devlog", "position: ${viewHolder.absoluteAdapterPosition}")
        (viewHolder.bindingAdapter as MissionAdapter).deleteItem(viewHolder.absoluteAdapterPosition)
    }

    /**
     * 정안되면 그냥 swipe 시 삭제로해..
     * 삭제 자체도 구현 없어도되고.
     * 우선은...도넛 그래프 ㅇㅋ?
     *
     * */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        Log.d("devlog", "action state: $actionState")
        Log.d("devlog", "dX: $dX")
        Log.d("devlog", "is currently active: $isCurrentlyActive")
        val itemView = viewHolder.itemView

        var measuredX = max(dX, -200f)
        measuredX = min(measuredX, 0f)

        paint.color = itemView.context.getColor(R.color.red)
        val rect = RectF(
            itemView.right + measuredX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat()
        )
        c.drawRect(rect, paint)

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            measuredX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return .1f
    }
}