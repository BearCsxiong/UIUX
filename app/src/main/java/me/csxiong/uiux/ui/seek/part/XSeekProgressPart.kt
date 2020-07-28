package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart
import me.csxiong.uiux.utils.print

/**
 * @Desc : 常规的SeekBar进度 带两种方向的SeekBar
 * @Author : Bear - 2020/7/27
 */
class XSeekProgressPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 进度矩形
     */
    val mProgressRectf: RectF = RectF()

    override fun onDraw(canvas: Canvas) {
        if (parent.isEnableExpandMode) {
            return
        }
        // 绘制进度
        if (parent.isEnableStroke) {
            parent.mProgressPaint.style = Paint.Style.FILL
        } else {
            parent.mProgressPaint.style = Paint.Style.FILL_AND_STROKE
        }
        canvas.drawRoundRect(mProgressRectf, 50f, 50f, parent.mProgressPaint)
    }

    override fun calculateDrawValue(fromUser: Boolean) {
        var centerPointPositionX = if (parent.centerPointPercent == 0.0f) parent.paddingLeft + parent.strokeWidth * 3 else parent.centerPointPositionX
        val left = if (parent.progressX < parent.centerPointPositionX) parent.progressX else centerPointPositionX
        val right = if (parent.progressX < parent.centerPointPositionX) centerPointPositionX else parent.progressX
        mProgressRectf[left, parent.customHeight / 2f - parent.mSeekBarHeight / 2f, right] = parent.customHeight / 2f + parent.mSeekBarHeight / 2f
        "${mProgressRectf.toShortString()}".print()
    }

}