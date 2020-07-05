package utn.frba.mobile.konzern.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class ScaleLayoutManager : LinearLayoutManager {
    private var mShrinkAmount = 0.05f
    private var mShrinkDistance = 1f

    constructor(context: Context?) : super(context) {}
    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(context, orientation, reverseLayout) {
    }

    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean,
        shrinkAmount: Float
    ) : this(context, orientation, reverseLayout) {
        mShrinkAmount = shrinkAmount
    }

    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean,
        shrinkAmount: Float,
        shrinkDistance: Float
    ) : this(context, orientation, reverseLayout, shrinkAmount) {
        mShrinkDistance = shrinkDistance
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint =
                    (getDecoratedBottom(child!!) + getDecoratedTop(child)) / 2f
                val d =
                    Math.min(d1, Math.abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }

    override fun onLayoutChildren(
        recycler: Recycler,
        state: RecyclerView.State
    ) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler,
        state: RecyclerView.State
    ): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint =
                    (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val d =
                    Math.min(d1, Math.abs(midpoint - childMidpoint))
                var scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                scale = if (java.lang.Float.isNaN(scale)) 0f else scale
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }
}