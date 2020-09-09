package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar拖动的滑块
 * @Author : Bear - 2020/7/27
 */
class XSeekCenterExpandThumbPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 中心点X
     */
    var cx = 0f

    /**
     * 中心点Y
     */
    var cy = 0f

    override fun onDraw(canvas: Canvas) {
        // 绘制手指拖动的thumb
        canvas.drawCircle(cx, cy, parent.mThumbRadius.toFloat(), parent.mProgressPaint)
        // 描边
        if (parent.isEnableStroke) {
            canvas.drawCircle(cx, cy, parent.mThumbRadius + parent.strokeWidth, parent.mStrokePaint)
        }
    }

    override fun calculateDrawValue( fromUser: Boolean) {
        cx = parent.progressX
        cy = parent.customHeight / 2f
    }

}