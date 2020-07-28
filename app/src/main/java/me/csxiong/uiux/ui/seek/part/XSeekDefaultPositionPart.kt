package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.support.annotation.FloatRange
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar默认位置
 * @Author : Bear - 2020/7/27
 */
class XSeekDefaultPositionPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 中心点X
     */
    var cx = 0f

    /**
     * 中心点Y
     */
    var cy = 0f

    override fun onDraw(canvas: Canvas) {
        // 默认位置点
        if (parent.defaultProgress != 0) {
            canvas.drawCircle(cx, cy, parent.defaultRadius.toFloat(), parent.mProgressPaint)
            //描边
            if (parent.isEnableStroke) {
                canvas.drawCircle(cx, cy, parent.defaultRadius + parent.strokeWidth, parent.mStrokePaint)
            }
        }
    }


    override fun calculateDrawValue(fromUser: Boolean) {
        parent.defaultPositionX = parent.barWidth * parent.defaultPosition + parent.paddingLeft + (1 + parent.defaultPosition) * parent.mThumbRadius + parent.strokeWidth * 2 - parent.defaultRadius
        cx = parent.defaultPositionX
        cy = parent.customHeight / 2f
    }

    /**
     * 设置默认位置
     *
     * @param defaultPosition
     */
    fun setDefaultPosition(@FloatRange(from = 0.0, to = 1.0) defaultPosition: Float) { // 计算推荐值的位置问题 使用真实计算bar长 但是又要以右为终点
        parent.defaultProgress = (defaultPosition * (parent.maxProgress - parent.minProgress)).toInt()
    }

}