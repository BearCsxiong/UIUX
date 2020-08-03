package me.csxiong.uiux.ui.layoutManager.active

import android.graphics.Rect

/**
 * @Desc : 居中粘性策略
 * @Author : Bear - 2020/6/19
 */
class CenterCrossActiveStrategy(val isVertical: Boolean, val centerOffset: Float) : ActiveStrategy {

    override fun onCalculatePercent(childRect: Rect, parentRect: Rect): Float {
        if (centerOffset == 0f) {
            if (isVertical) {
                return 1 - Math.abs(childRect.exactCenterY() - parentRect.exactCenterY()) / (parentRect.height().toFloat() / 2)
            } else {
                return 1 - Math.abs(childRect.exactCenterX() - parentRect.exactCenterX()) / (parentRect.width().toFloat() / 2)
            }
        } else {
            var disOff = if (isVertical) Math.abs(childRect.exactCenterY() - parentRect.exactCenterY()) else Math.abs(childRect.exactCenterX() - parentRect.exactCenterX())
            if (disOff > centerOffset) {
                return 0f
            }
            disOff = Math.abs(disOff - centerOffset);
            return disOff / centerOffset
        }
    }

}