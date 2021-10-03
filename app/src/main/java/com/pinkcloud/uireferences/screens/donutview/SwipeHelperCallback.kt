package com.pinkcloud.uireferences.screens.donutview

import android.graphics.*
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
//        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        if (direction == ItemTouchHelper.RIGHT) viewHolder.bindingAdapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)

        (viewHolder.bindingAdapter as MissionAdapter).deleteItem(viewHolder.absoluteAdapterPosition)
    }

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
        val itemView = viewHolder.itemView

//        var measuredX = max(dX, -200f)
//        measuredX = min(measuredX, 0f)
        var measuredX = dX

        paint.color = itemView.context.getColor(R.color.red)
        val rect = RectF(
            itemView.right + measuredX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat()
        )
        c.drawRect(rect, paint)
        val label = itemView.context.getString(R.string.delete)
        paint.color = Color.WHITE
        c.drawText(label, (rect.left + rect.right)/2, (rect.top + rect.bottom)/2 - ((paint.descent() + paint.ascent()) / 2), paint)

//        Log.d("devlog","measured x: $measuredX")

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
}