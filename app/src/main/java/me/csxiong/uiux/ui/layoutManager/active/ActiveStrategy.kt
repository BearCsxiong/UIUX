package me.csxiong.uiux.ui.layoutManager.active

import android.graphics.Rect

/**
 * @Desc : 粘性策略
 * @Author : Bear - 2020/6/19
 */
interface ActiveStrategy {

    /**
     * 比较百分比 越接近1 越符合
     */
    fun onCalculatePercent(childRect: Rect, parentRect: Rect): Float
}