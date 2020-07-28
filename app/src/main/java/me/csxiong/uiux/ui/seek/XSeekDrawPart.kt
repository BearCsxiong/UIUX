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
     * 初始化尺寸
     *
     * @param width  控件总宽度
     * @param height 控件总高度
     */
    abstract fun initSize(width: Int, height: Int)

    /**
     * 进度改变回调
     */
    abstract fun onProgressChange(progressX: Float, progress: Float, intProgress: Int, fromUser: Boolean)

}