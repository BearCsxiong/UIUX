package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar拖动的滑块
 * @Author : Bear - 2020/7/27
 */
class XSeekThumbPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    override fun onDraw(canvas: Canvas) {
        // 绘制手指拖动的thumb
        canvas.drawCircle(parent.limitLeft + parent.barWidth * parent.progressPercent, parent.customHeight / 2f, parent.mThumbRadius.toFloat(), parent.mProgressPaint)
        if (parent.isEnableStroke) { // 描边
            canvas.drawCircle(parent.limitLeft + parent.barWidth * parent.progressPercent, parent.customHeight / 2f, parent.mThumbRadius + parent.strokeWidth,
                    parent.mStrokePaint)
        }
    }

    override fun initSize(width: Int, height: Int) {

    }
}