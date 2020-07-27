package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar进度
 * @Author : Bear - 2020/7/27
 */
class XSeekProgressPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    override fun onDraw(canvas: Canvas) {
        // 绘制进度
        if (parent.mProgressRectf != null) {
            canvas.drawRoundRect(parent.mProgressRectf, 50f, 50f, parent.mProgressPaint)
        }
    }

    override fun initSize(width: Int, height: Int) {
    }

}