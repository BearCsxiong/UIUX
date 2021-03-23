package me.csxiong.uiux.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.library.utils.XResUtils
import me.csxiong.uiux.R

/**
 * @Desc : 描边控件
 * @Author : meitu - 3/18/21
 *
 * 比较解耦的处理描边问题 如果是文字和iconfont图标的描边问题
 */
class StrokeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val fullRect = RectF()
    val drawRect = RectF()

    val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = XResUtils.getColor(R.color.black5)
        }
    }

    var corner = XDisplayUtil.dpToPx(4f)
        set(value) {
            field = value
            invalidate()
        }

    var isStroke = true
        set(value) {
            field = value
            invalidate()
        }

    var strokeWidth: Float = 2f
        set(value) {
            field = value
            paint.strokeWidth = value
            drawRect.set(fullRect)
            drawRect.inset(strokeWidth / 2, strokeWidth / 2)
            invalidate()
        }

    var strokeColor: Int = XResUtils.getColor(R.color.black5)
        set(value) {
            field = value
            paint.color = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isStroke) {
            canvas?.drawRoundRect(drawRect, corner, corner, paint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fullRect.set(0f, 0f, w.toFloat(), h.toFloat())
        drawRect.set(fullRect)
        drawRect.inset(strokeWidth / 2, strokeWidth / 2f)
    }

}