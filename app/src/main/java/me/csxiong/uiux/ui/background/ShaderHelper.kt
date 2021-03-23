package me.csxiong.uiux.ui.background

import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient

/**
 * @Desc : 创建shader
 * @Author : meitu - 3/18/21
 */
class ShaderHelper {

    private var mWidth = 0

    private var mHeight = 0

    var shader: Shader? = null

    var backgroundType: BackgroundType? = null

    fun applyBackgroundType(backgroundType: BackgroundType?) {
        this.backgroundType = backgroundType
        shader = when (backgroundType?.type) {
            BackgroundType.Type.HORIZONTAL -> {
                LinearGradient(-mWidth / 2f, 0f, mWidth / 2f, 0f, backgroundType.colors, backgroundType.positions, Shader.TileMode.CLAMP)
            }
            BackgroundType.Type.VERTICAL -> {
                LinearGradient(0f, mHeight / 2f, 0f, -mHeight / 2f, backgroundType.colors, backgroundType.positions, Shader.TileMode.CLAMP)
            }
            BackgroundType.Type.RADIAL -> {
                RadialGradient(0f, 0f, mHeight / 2f, backgroundType.colors, backgroundType.positions, Shader.TileMode.CLAMP)
            }
            BackgroundType.Type.SWEEP -> {
                SweepGradient(0f, 0f, backgroundType.colors, backgroundType.positions)
            }
            else -> null
        }
    }

    fun applySize(width: Int, height: Int) {
        mWidth = width
        mHeight = height
        applyBackgroundType(backgroundType)
    }
}