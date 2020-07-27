package me.csxiong.uiux.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import me.csxiong.library.utils.XAnimator
import me.csxiong.library.utils.XAnimatorCaculateValuer
import me.csxiong.library.utils.XDisplayUtil

class XCustomSeekBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val backPath = Path()

    val leftHeight = XDisplayUtil.dpToPx(4f)

    var rightHeight = leftHeight

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        backPath.reset()
        backPath.moveTo(leftHeight, customHeight / 2f - leftHeight / 2f)
        backPath.arcTo(0f, customHeight / 2f - leftHeight / 2, leftHeight, customHeight / 2f + leftHeight / 2f, 270f, -180f, false)
        backPath.lineTo(customWidth.toFloat() - rightHeight, customHeight / 2f + leftHeight / 2f)
        backPath.arcTo(customWidth.toFloat() - rightHeight, customHeight / 2f - (rightHeight - leftHeight / 2f), customWidth.toFloat(), customHeight / 2f + leftHeight / 2f, 90f, -180f, false)
        backPath.close()
        canvas?.drawPath(backPath, paint)
    }

    var customWidth = 0

    var customHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        customWidth = w
        customHeight = h
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        customWidth = right - left
        customHeight = bottom - top
    }


    fun start() {
        animator.cancel()
        isHeight = !isHeight
        heightValuer.mark(rightHeight, if (isHeight) XDisplayUtil.dpToPx(20f) else XDisplayUtil.dpToPx(6f))
        animator.start()
    }

    var isHeight = false

    var heightValuer = XAnimatorCaculateValuer()

    val animator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(object : XAnimator.XAnimationListener {
                override fun onAnimationEnd(animation: XAnimator?) {
                }

                override fun onAnimationCancel(animation: XAnimator?) {
                }

                override fun onAnimationStart(animation: XAnimator?) {
                }

                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    rightHeight = heightValuer.caculateValue(fraction)
                    invalidate()
                }
            })

}