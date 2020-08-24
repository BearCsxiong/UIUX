package me.csxiong.uiux.ui.vernier

import me.csxiong.library.utils.MathUtil
import me.csxiong.library.utils.XDisplayUtil

/**
 * @Desc : Fling计算手势快速滑动工具
 * @Author : Bear - 2020/8/21
 */
class FlingCalculateValuer {

    /**
     * 需要调教
     */
    companion object {
        val DAMP_SLOW = 8f
        val DAMP_NORMAL = 6f
        val DAMP_FAST = 4f
    }

    var deltaX = 0f
    var deltaY = 0f
    var time: Long = 0
    var damp = DAMP_NORMAL

    fun calculate(velocityX: Float, velocityY: Float) {
        deltaX = velocityX / damp
        deltaY = velocityY / damp
        time = 250.toLong() + MathUtil.getRatioValue(0, 150, Math.max(Math.abs(deltaX), Math.abs(deltaY)) / XDisplayUtil.dpToPx(200f))
    }

}