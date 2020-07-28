package me.csxiong.uiux.ui.seek

import android.graphics.Canvas

/**
 * @Desc : SeekBar绘制组成部分
 * @Author : Bear - 2020/7/27
 */
abstract class XSeekDrawPart(val parent: XSeekBar) {

    /**
     * 绘制部分
     *
     * @param canvas
     */
    abstract fun onDraw(canvas: Canvas)

    /**
     * 重新计算绘制值
     */
    abstract fun calculateDrawValue(fromUser: Boolean)

}