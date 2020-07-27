package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar进度
 * @Author : Bear - 2020/7/27
 */
class XSeekProgressPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 进度矩形
     */
    val mProgressRectf: RectF = RectF()

    override fun onDraw(canvas: Canvas) {
        // 绘制进度
        canvas.drawRoundRect(mProgressRectf, 50f, 50f, parent.mProgressPaint)
    }

    override fun initSize(width: Int, height: Int) {
    }

    override fun onProgressChange(progressPercent: Float, progress: Float, intProgress: Int, fromUser: Boolean) {
        val left = if (progressPercent > parent.centerPointPercent) parent.paddingLeft + parent.strokeWidth * 2 + parent.centerPointPercent * parent.backgroundWidth else parent.backgroundWidth * progressPercent + parent.paddingLeft + parent.strokeWidth * 2
        val right = if (progressPercent > parent.centerPointPercent) parent.backgroundWidth * progressPercent + parent.paddingRight + parent.strokeWidth * 2 else parent.paddingRight + parent.strokeWidth * 2 + parent.centerPointPercent * parent.backgroundWidth
        mProgressRectf[left, parent.customHeight / 2f - parent.mSeekBarHeight / 2f, right] = parent.customHeight / 2f + parent.mSeekBarHeight / 2f
    }

}