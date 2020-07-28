package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar拖动的滑块
 * @Author : Bear - 2020/7/27
 */
class XSeekThumbPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

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

    override fun initSize(width: Int, height: Int) {

    }

    override fun onProgressChange(progressX: Float, progress: Float, intProgress: Int, fromUser: Boolean) {
        calculateThumb()
    }

    fun calculateThumb() {
        cx = parent.progressX
        var percent = (parent.progressX - parent.limitLeft) / parent.barWidth
        cy = parent.customHeight / 2f - (parent.contentHeight - parent.mSeekBarHeight) * percent / 2f
    }
}