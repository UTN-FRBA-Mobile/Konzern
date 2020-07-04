package utn.frba.mobile.konzern.home

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class SnapItemDecorator(val context: Context?) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val cardWidthPercent = 0.9f
        val viewWidth = parent.layoutManager!!.width
        val cardWidth = (viewWidth * cardWidthPercent).toInt()
        val offset = (viewWidth - cardWidth) / 3

        view.layoutParams.width = cardWidth - offset * 3
        view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        when {
            parent.getChildAdapterPosition(view) == 0 -> {
                outRect.right = -(offset / 3)
                outRect.left = offset * 3
            }
            parent.getChildAdapterPosition(view) == state.itemCount - 1 -> {
                outRect.left = -(offset / 3)
                outRect.right = offset * 3
            }
            else ->{
                outRect.left = -(offset / 3)
                outRect.right = -(offset / 3)
            }
        }
    }
}