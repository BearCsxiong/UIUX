package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart
import me.csxiong.uiux.utils.print

/**
 * @Desc : Seekbar扩张的Progress内容部分
 * @Author : Bear - 2020/7/28
 */
class XSeekCenterExpandProgressPart(parent: XSeekBar) : XSeekDrawPart(parent) {

    /**
     * 进度Path
     */
    val progressPath: Path = Path()

    val leftRectF: RectF = RectF()

    val rightRectF: RectF = RectF()

    var rightHeight = parent.contentHeight

    override fun onDraw(canvas: Canvas) {
        if (!parent.isEnableExpandMode) {
            return
        }
        // 绘制进度
        if (parent.isEnableStroke) {
            parent.mProgressPaint.style = Paint.Style.FILL
        } else {
            parent.mProgressPaint.style = Paint.Style.FILL_AND_STROKE
        }
        "onDraw".print()
        canvas.drawPath(progressPath, parent.mProgressPaint)
    }

    override fun calculateDrawValue(fromUser: Boolean) {
        var centerPointPositionX = if (parent.centerPointPercent == 0.0f) parent.paddingLeft + parent.strokeWidth else parent.centerPointPositionX
        val left = if (parent.progressX < parent.centerPointPositionX) parent.progressX else centerPointPositionX
        val right = if (parent.progressX < parent.centerPointPositionX) centerPointPositionX else parent.progressX
        var barHeight = 0f
        if (parent.isEnableStroke) {
            barHeight = parent.mSeekBarHeight
        } else {
            barHeight = parent.mSeekBarHeight + parent.strokeWidth * 2
        }
        val percent = parent.calculateProgressPercent(parent.progress)
        rightHeight = barHeight + (parent.contentHeight - barHeight) * percent
//        "${rightHeight} -- ${barHeight} -- ${percent}".print("csx")
        progressPath.reset()
        progressPath.moveTo(left + barHeight, parent.customHeight / 2f - barHeight / 2f)
        leftRectF.set(left, parent.customHeight / 2f - barHeight / 2, left + barHeight, parent.customHeight / 2f + barHeight / 2f)
        progressPath.arcTo(leftRectF, 270f, -180f, false)
        progressPath.lineTo(right - rightHeight, parent.customHeight / 2f + rightHeight/2f)
        rightRectF.set(right - rightHeight, parent.customHeight / 2f - rightHeight / 2f, right, parent.customHeight / 2f + rightHeight / 2f)
        progressPath.arcTo(rightRectF, 90f, -180f, false)
        progressPath.close()
    }
}