package me.csxiong.uiux.ui.disseek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DisSeekBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.RED
    }

    val rectF = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}