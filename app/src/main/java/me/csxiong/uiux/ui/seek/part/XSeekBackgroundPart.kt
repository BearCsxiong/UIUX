package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar背景
 * @Author : Bear - 2020/7/27
 */
class XSeekBackgroundPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

//    /**
//     * 背景描边矩形
//     */
//    val mBackgroundStrokeRectf: RectF = RectF()
//
//    /**
//     * 背景矩形
//     */
//    val mBackgroundRectf: RectF = RectF()

    /**
     * 背景Path
     */
    val backgroundPath: Path = Path()

    /**
     * 背景描边
     */
    val backgroundStrokePath: Path = Path()

    val leftRectF: RectF = RectF()

    val rightRectF: RectF = RectF()

    val rightHeight = parent.mSeekBarHeight

    override fun onDraw(canvas: Canvas) {
        // 绘制背景
        canvas.drawPath(backgroundPath, parent.mBackgroundPaint)
        // 绘制描边
        if (parent.isEnableStroke) {
            canvas.drawPath(backgroundStrokePath, parent.mStrokePaint)
        }
    }

    override fun initSize(width: Int, height: Int) {
//        // 初始化基础图形结构
//        mBackgroundRectf[parent.paddingLeft + parent.strokeWidth * 2, height / 2f - parent.mSeekBarHeight / 2f, width - parent.paddingRight - parent.strokeWidth * 2] = height / 2f + parent.mSeekBarHeight / 2f
//        // 背景描边
//        mBackgroundStrokeRectf[mBackgroundRectf.left - parent.strokeWidth, mBackgroundRectf.top - parent.strokeWidth, mBackgroundRectf.right + parent.strokeWidth] = mBackgroundRectf.bottom + parent.strokeWidth
        var limitLeft = parent.paddingLeft + parent.strokeWidth * 2
        var halfHeight = parent.mSeekBarHeight / 2


        backgroundPath.reset()
        backgroundPath.moveTo(limitLeft + parent.mSeekBarHeight, parent.customHeight / 2f - parent.mSeekBarHeight / 2f)
        leftRectF.set(limitLeft, parent.customHeight / 2f - parent.mSeekBarHeight / 2, limitLeft + parent.mSeekBarHeight, parent.customHeight / 2f + parent.mSeekBarHeight / 2f)
        backgroundPath.arcTo(leftRectF, 270f, -180f, false)
        backgroundPath.lineTo(parent.customWidth - parent.paddingRight - parent.strokeWidth * 2 - rightHeight, parent.customHeight / 2f + parent.mSeekBarHeight / 2f)
        rightRectF.set(parent.customWidth - parent.paddingRight - parent.strokeWidth * 2 - rightHeight, parent.customHeight / 2f - (rightHeight - parent.mSeekBarHeight / 2f), parent.customWidth.toFloat() - parent.paddingRight - parent.strokeWidth * 2, parent.customHeight / 2f + parent.mSeekBarHeight / 2f)
        backgroundPath.arcTo(rightRectF, 90f, -180f, false)
        backgroundPath.close()

        backgroundStrokePath.set(backgroundPath)
    }

    override fun onProgressChange(progressPercent: Float, progress: Float, intProgress: Int, fromUser: Boolean) {

    }

}