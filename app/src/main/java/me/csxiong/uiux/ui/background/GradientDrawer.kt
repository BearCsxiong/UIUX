package me.csxiong.uiux.ui.background

import android.graphics.*
import android.view.View

/**
 * @Desc : 渐变绘制
 * @Author : meitu - 3/18/21
 */
class GradientDrawer(val parent: View) {

    var mWidth = 0

    var mHeight = 0

    val backgroundPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    val path = Path()

    var backgroundType: BackgroundType? = null

    var shaderHelper = ShaderHelper()

    fun onDraw(canvas: Canvas) {
        backgroundType?.let {
            canvas.save()
            canvas.translate(mWidth / 2f, mHeight / 2f)
            canvas.rotate(it.angle.toFloat())
            backgroundPaint.shader = shaderHelper.shader
            path.addCircle(0f, 0f, mHeight / 2f, Path.Direction.CW)
            canvas.drawPath(path, backgroundPaint)
            canvas.restore()
        }
    }

    fun applyBackgroundType(backgroundType: BackgroundType) {
        this.backgroundType = backgroundType
        parent.post {
            shaderHelper.applyBackgroundType(backgroundType)
            parent.invalidate()
        }
    }

    fun onSizeChange(mWidth: Int, mHeight: Int) {
        this.mWidth = mWidth
        this.mHeight = mHeight
        shaderHelper.applySize(mWidth, mHeight)
        parent.postInvalidate()
    }
}