package me.csxiong.uiux.ui.background

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * @Desc : 背景控件
 * @Author : meitu - 3/11/21
 */
class BackgroundItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val gradientDrawer = GradientDrawer(this)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gradientDrawer.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gradientDrawer.onSizeChange(w,h)
    }


}