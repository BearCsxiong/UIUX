package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar进度
 * @Author : Bear - 2020/7/27
 */
class XSeekProgressPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 进度Path
     */
    val progressPath: Path = Path()

    /**
     * 进度矩形
     */
    val mProgressRectf: RectF = RectF()

    override fun onDraw(canvas: Canvas) {
        // 绘制进度
        if (parent.isEnableStroke) {
            parent.mProgressPaint.style = Paint.Style.FILL
        } else {
            parent.mProgressPaint.style = Paint.Style.FILL_AND_STROKE
        }
        canvas.drawRoundRect(mProgressRectf, 50f, 50f, parent.mProgressPaint)
    }

    override fun initSize(width: Int, height: Int) {
    }

    override fun onProgressChange(progressX: Float, progress: Float, intProgress: Int, fromUser: Boolean) {
        var centerPointPositionX = if (parent.centerPointPercent == 0.0f) parent.paddingLeft + parent.strokeWidth else parent.centerPointPositionX
        val left = if (progressX < parent.centerPointPositionX) progressX else centerPointPositionX
        val right = if (progressX < parent.centerPointPositionX) centerPointPositionX else progressX
        mProgressRectf[left, parent.customHeight / 2f - parent.mSeekBarHeight / 2f, right] = parent.customHeight / 2f + parent.mSeekBarHeight / 2f
    }

}