package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar背景
 * @Author : Bear - 2020/7/27
 */
class XSeekBackgroundPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    override fun onDraw(canvas: Canvas) {
        // 绘制背景
        if (parent.mBackgroundRectf != null) {
            canvas.drawRoundRect(parent.mBackgroundRectf, 50f, 50f, parent.mBackgroundPaint)
        }
        // 绘制描边
        if (parent.isEnableStroke && parent.mBackgroundStrokeRectf != null) {
            canvas.drawRoundRect(parent.mBackgroundStrokeRectf, 50f, 50f, parent.mStrokePaint)
        }
    }

    override fun initSize(width: Int, height: Int) {
    }

}