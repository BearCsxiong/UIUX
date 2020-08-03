package me.csxiong.uiux.ui.book

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import me.csxiong.library.utils.XDisplayUtil

/**
 * @Desc : book的分割
 * @Author : Bear - 2020/8/3
 */
class BookItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.top = XDisplayUtil.dpToPxInt(15f)
            outRect.bottom = XDisplayUtil.dpToPxInt(15f)
        } else {
            outRect.bottom = XDisplayUtil.dpToPxInt(15f)
        }

    }
}