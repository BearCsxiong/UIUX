package me.csxiong.uiux.ui.layoutManager.active

import android.graphics.Rect

/**
 * @Desc : 居中粘性策略
 * @Author : Bear - 2020/6/19
 */
class CenterActiveStrategy(val isVertical: Boolean) : ActiveStrategy {

    override fun onCalculatePercent(childRect: Rect, parentRect: Rect): Float {
        if (isVertical) {
            return 1 - Math.abs(childRect.exactCenterY() - parentRect.exactCenterY()) / (parentRect.height().toFloat() / 2)
        } else {
            return 1 - Math.abs(childRect.exactCenterX() - parentRect.exactCenterX()) / (parentRect.width().toFloat() / 2)
        }
    }
}