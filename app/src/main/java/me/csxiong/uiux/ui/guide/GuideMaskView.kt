package me.csxiong.uiux.ui.guide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.utils.ViewUtils

/**
 * @Desc : 抠图控件
 * @Author : Bear - 2020/9/25
 */
class GuideMaskView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        val PADDING = XDisplayUtil.dpToPxInt(7.5f)
    }

    init {
        setWillNotDraw(false)
    }

    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xff000000.toInt()
            alpha = 51
        }
    }

    private val mLinePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xffffffff.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 3f
            pathEffect = DashPathEffect(floatArrayOf(3f, 3f), 0f)
        }
    }

    private val mPointPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xffffffff.toInt()
        }
    }

    private var enableLineIndicator = true

    private val indicatorCircleRadius = XDisplayUtil.dpToPx(2.5f)

    private val maskPath = Path()

    private val fullRect = RectF()

    private val clipRect = RectF()

    private val lineStartPoint = PointF()

    private val lineEndPoint = PointF()

    private val lineLength = XDisplayUtil.dpToPx(20f)

    /**
     * 偏移中心
     */
    var offset = XDisplayUtil.dpToPx(50f)
        set(value) {
            field = value
            invalidate()
        }

    private var targetX = 0

    private var targetY = 0

    /**
     * 是否是圆形
     */
    var isOval = true
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        maskPath.reset()
        maskPath.addRect(fullRect, Path.Direction.CCW)
        clipRect.set(targetX - offset, targetY - offset, targetX + offset, targetY + offset)
        if (isOval) {
            maskPath.addOval(clipRect, Path.Direction.CW)
        } else {
            maskPath.addRect(clipRect, Path.Direction.CW)
        }
        canvas?.drawPath(maskPath, mPaint)

        if (enableLineIndicator) {
            var isTop = Math.abs(fullRect.top - clipRect.top) > Math.abs(fullRect.bottom - clipRect.bottom)
            if (isTop) {
                lineStartPoint.set(clipRect.centerX(), clipRect.centerY() - offset - PADDING)
                lineEndPoint.set(clipRect.centerX(), clipRect.centerY() - offset - lineLength - PADDING)
            } else {
                lineStartPoint.set(clipRect.centerX(), clipRect.centerY() + offset + PADDING)
                lineEndPoint.set(clipRect.centerX(), clipRect.centerY() + offset + lineLength + PADDING)
            }
            canvas?.drawCircle(lineStartPoint.x, lineStartPoint.y, indicatorCircleRadius, mPointPaint)
            canvas?.drawLine(lineStartPoint.x, lineStartPoint.y, lineEndPoint.x, lineEndPoint.y, mLinePaint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSize(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initSize(right - left, bottom - top)
    }

    fun initSize(width: Int, height: Int) {
        fullRect.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    fun clip(view: View?) {
        clip(view, false)
    }

    /**
     * BugFix 修复在全屏下的偏移问题
     */
    fun clip(view: View?, isFullScreen: Boolean) {
        post {
            val rect = ViewUtils.getGlobalVisibleRect(view)
            rect?.let {
                if (!isFullScreen) {
                    rect.offset(0, -XDisplayUtil.getStatusBarHeight())
                }
                targetX = it.centerX()
                targetY = it.centerY()
            }
            invalidate()
        }
    }
}