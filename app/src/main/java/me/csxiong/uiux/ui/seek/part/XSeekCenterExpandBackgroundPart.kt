package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart
import me.csxiong.uiux.utils.print

/**
 * @Desc : SeekBar背景
 * @Author : Bear - 2020/7/27
 */
class XSeekCenterExpandBackgroundPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {
    /**
     * 背景Path
     */
    val backgroundPath: Path = Path()

    val leftRectF: RectF = RectF()

    val rightRectF: RectF = RectF()

    var rightHeight = parent.contentHeight + parent.strokeWidth * 2

    override fun onDraw(canvas: Canvas) {
        // 绘制背景
        canvas.drawPath(backgroundPath, parent.mBackgroundPaint)
        // 绘制描边
        if (parent.isEnableStroke) {
            canvas.drawPath(backgroundPath, parent.mStrokePaint)
        }
    }

    override fun calculateDrawValue(fromUser: Boolean) {
        var barHeight = parent.mSeekBarHeight + parent.strokeWidth * 2
        rightHeight = parent.contentHeight + parent.strokeWidth * 2
        var limitLeft = parent.paddingLeft + parent.strokeWidth * 2
        "${barHeight} -- ${rightHeight}".print()

        backgroundPath.reset()
        backgroundPath.moveTo(limitLeft + barHeight, parent.customHeight / 2f - barHeight / 2f)
        leftRectF.set(limitLeft, parent.customHeight / 2f - barHeight / 2, limitLeft + barHeight, parent.customHeight / 2f + barHeight / 2f)
        backgroundPath.arcTo(leftRectF, 270f, -180f, false)
        backgroundPath.lineTo(parent.customWidth - parent.paddingRight - parent.strokeWidth * 2 - rightHeight, parent.customHeight / 2f + parent.contentHeight / 2f)
        rightRectF.set(parent.customWidth - parent.paddingRight - parent.strokeWidth * 2 - rightHeight, parent.customHeight / 2f - parent.contentHeight / 2, parent.customWidth.toFloat() - parent.paddingRight - parent.strokeWidth * 2, parent.customHeight / 2f + parent.contentHeight / 2f)
        backgroundPath.arcTo(rightRectF, 90f, -180f, false)
        backgroundPath.close()
    }

}