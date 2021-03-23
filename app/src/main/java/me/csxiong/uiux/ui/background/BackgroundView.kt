package me.csxiong.uiux.ui.background

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange

/**
 * @Desc : 背景控件
 * @Author : meitu - 3/16/21
 */
class BackgroundView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val backgroundPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    var bitmapShader: BitmapShader? = null

    var shaderHelper = ShaderHelper()

    var xfermode: PorterDuffXfermode? = PorterDuffXfermode(PorterDuff.Mode.OVERLAY)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (bitmapShader != null) {
            canvas?.save()
            canvas?.translate(mWidth / 2f, mHeight / 2f)
            shaderHelper.backgroundType?.angle?.let {
                canvas?.rotate(it.toFloat())
            }
            backgroundPaint.alpha = 255
            backgroundPaint.shader = shaderHelper.shader
            canvas?.drawPaint(backgroundPaint)
            canvas?.restore()
            backgroundPaint.shader = bitmapShader
            backgroundPaint.alpha = bitmapAlpha
            backgroundPaint.xfermode = xfermode
            canvas?.drawPaint(backgroundPaint)
            backgroundPaint.xfermode = null
        }
    }

    var mWidth = 0
    var mHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        shaderHelper.applySize(w, h)
    }


    fun setMixture(bitmap: Bitmap?, backgroundType: BackgroundType?, mode: PorterDuff.Mode = PorterDuff.Mode.OVERLAY) {
        post {
            xfermode = PorterDuffXfermode(mode)
            shaderHelper.applyBackgroundType(backgroundType)
            if (bitmap != null) {
                bitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            } else {
                bitmapShader = null
            }
            invalidate()
        }
    }

    var bitmapAlpha = 255

    fun setAlpha(@IntRange(from = 0, to = 255) alpha: Int) {
        bitmapAlpha = alpha
        postInvalidate()
    }


}