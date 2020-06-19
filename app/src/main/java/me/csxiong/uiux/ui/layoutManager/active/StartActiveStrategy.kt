package me.csxiong.uiux.ui.layoutManager.active

import android.graphics.Rect

/**
 * @Desc : 起始位置策略
 * @Author : Bear - 2020/6/19
 */
class StartActiveStrategy(val isVertical: Boolean) : ActiveStrategy {

    override fun onCalculatePercent(childRect: Rect, parentRect: Rect): Float {
        return 0f;
    }
}