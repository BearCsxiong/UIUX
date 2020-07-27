package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import android.graphics.RectF
import android.support.annotation.FloatRange
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar中心点部分
 * @Author : Bear - 2020/7/27
 */
class XSeekCenterPositionPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    /**
     * 中心点矩形
     */
    private val mCenterPointRectf: RectF? = RectF()
    /**
     * 中心描边矩形
     */
    private val mCenterStrokePointRectf: RectF? = RectF()

    override fun onDraw(canvas: Canvas) {
        // 绘制中心点
        if (parent.isEnableCenterPoint && mCenterPointRectf != null) {
            canvas.drawRoundRect(mCenterPointRectf, 50f, 50f, parent.mProgressPaint)
            if (parent.isEnableStroke && mCenterStrokePointRectf != null) { // 绘制中心描边
                canvas.drawRoundRect(mCenterStrokePointRectf, 50f, 50f, parent.mStrokePaint)
            }
        }
    }

    override fun initSize(width: Int, height: Int) {

    }

    override fun onProgressChange(progressPercent: Float, progress: Float, intProgress: Int, fromUser: Boolean) {
    }

    /**
     * 设置中心比例
     *
     * @param centerPointPercent
     */
    fun setCenterPointPercent(@FloatRange(from = 0.0, to = 1.0) centerPointPercent: Float) {
        // 设置中心的Rectf
        mCenterPointRectf!![parent.limitLeft + parent.barWidth * centerPointPercent - parent.mCenterPointWidth / 2f, parent.customHeight / 2f - parent.mCenterPointHeight / 2f, parent.limitLeft + parent.barWidth * centerPointPercent + parent.mCenterPointWidth / 2f] = parent.customHeight / 2f + parent.mCenterPointHeight / 2f
        mCenterStrokePointRectf!![mCenterPointRectf.left - parent.strokeWidth, mCenterPointRectf.top - parent.strokeWidth, mCenterPointRectf.right + parent.strokeWidth] = mCenterPointRectf.bottom + parent.strokeWidth
        parent.invalidate()
    }

}