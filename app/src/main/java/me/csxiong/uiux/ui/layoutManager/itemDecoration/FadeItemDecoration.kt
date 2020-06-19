package me.csxiong.uiux.ui.layoutManager.itemDecoration

import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.RecyclerView

/**
 * @Desc : 渐变ItemDecoration
 * @Author : Bear - 2020/6/19
 */
class FadeItemDecoration : RecyclerView.ItemDecoration() {

    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

//    val linearGradient = LinearGradient()

    init {

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }


}