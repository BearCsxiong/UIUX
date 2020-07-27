package me.csxiong.uiux.ui.seek.part

import android.graphics.Canvas
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.ui.seek.XSeekBar
import me.csxiong.uiux.ui.seek.XSeekDrawPart

/**
 * @Desc : SeekBar中心指示器部分
 * @Author : Bear - 2020/7/27
 */
class XSeekThumbIndicatorPart(xSeekBar: XSeekBar) : XSeekDrawPart(xSeekBar) {

    override fun onDraw(canvas: Canvas) {
        // 绘制手指拖动的thumb中的indicator标记点
        if (parent.isEnableThumbIndicator) {
            canvas.drawCircle(parent.limitLeft + parent.barWidth * parent.progressPercent, parent.customHeight / 2f, XDisplayUtil.dpToPxInt(2.5f).toFloat(),
                    parent.mThumbIndicatorPaint)
        }
    }

    override fun initSize(width: Int, height: Int) {
    }
}
