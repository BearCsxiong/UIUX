package me.csxiong.uiux.ui.background

import androidx.annotation.IntDef

/**
 * @Desc :背景类型
 * @Author : meitu - 3/16/21
 */
class BackgroundType(@Type val type: Int, val angle: Int, val colors: IntArray, val positions: FloatArray) {

    /**
     * @Desc : 背景类型
     * @Author : meitu - 3/18/21
     */
    @IntDef(value = [Type.HORIZONTAL, Type.VERTICAL, Type.RADIAL, Type.SWEEP])
    annotation class Type {

        companion object {
            const val HORIZONTAL = 0
            const val VERTICAL = 1
            const val RADIAL = 2
            const val SWEEP = 3
        }
    }
}