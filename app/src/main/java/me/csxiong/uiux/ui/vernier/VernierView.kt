package me.csxiong.uiux.ui.vernier

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.OverScroller
import me.csxiong.library.utils.VibratorUtils
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.library.utils.gesture.XGestureDetector

/**
 * @Desc : 游标刻度滚动部分
 * @Author : Bear - 2020/8/21
 */
class VernierView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        /**
         * 过限阻尼
         */
        val DAMP_VALUE = 5

        /**
         * 每份分割的Progress
         */
        val PER_SPACE_PROGRESS = 10
    }

    /**
     * 每份分割的Progress
     */
    private var perSpaceProgress = PER_SPACE_PROGRESS

    /**
     * 宽度
     */
    private var mWidth: Int = 0

    /**
     * 高度
     */
    private var mHeight: Int = 0

    /**
     * 间隔
     */
    private var SPACE = XDisplayUtil.dpToPxInt(4f)

    /**
     * 初始位移
     */
    private var initTranslateX: Float = 0f

    /**
     * 最大Progress
     */
    var maxProgress = 450
        set(value) {
            field = value
            invalidate()
        }

    /**
     * 最小Progress
     */
    var minProgress = -450
        set(value) {
            field = value
            invalidate()
        }

    /**
     * 当前Progress
     */
    private var innerProgress: Int = 0

    /**
     * 设置progress
     */
    fun setProgress(progress: Int) {
        setProgress(progress, false)
    }

    /**
     * 设置progress
     */
    fun setProgress(progress: Int, withAnim: Boolean) {
        var fixProgress = progress
        if (progress <= minProgress) {
            fixProgress = minProgress
        } else if (progress >= maxProgress) {
            fixProgress = maxProgress
        }
        if (withAnim) {
            scroller.forceFinished(true)
            val finalScrollX = calculateScrollX(fixProgress)
            val dx = finalScrollX - scrollX
            scroller.startScroll(-scrollX, 0, -dx, 0)
            invalidate()
        } else {
            scroller.forceFinished(true)
            this.innerProgress = fixProgress
            scrollTo(calculateScrollX(fixProgress), 0)
            invalidate()
            onProgressChangeListener?.onProgressChange(fixProgress, scrollX, false)
        }
    }

    /**
     * 进度回调监听
     */
    var onProgressChangeListener: OnProgressChangeListener? = null

    /**
     * Scroller
     */
    private val scroller by lazy { OverScroller(context, AccelerateInterpolator()) }

    /**
     * 手势采集
     */
    private val xGestureDetector by lazy {
        XGestureDetector(context).apply {
            setOnGestureListener(object : XGestureDetector.OnGestureListener {

                override fun onScaleEnd() {
                }

                override fun onFling(startX: Float, startY: Float, velocityX: Float, velocityY: Float) {
                    if (scrollX <= 0 || scrollX >= SPACE * getSpaceCount()) {
                        return
                    }
                    scroller.fling(-scrollX, 0, velocityX.toInt(), 0, -(SPACE * getSpaceCount()).toInt(), 0, 0, 0, 200, 0)
                    invalidate()
                }

                override fun onDoubleTap(event: MotionEvent?) {
                }

                override fun onScale(scaleFraction: Float, focusX: Float, focusY: Float) {
                }

                override fun onScroll(distanceX: Float, distanceY: Float) {
                    if (!scroller.isFinished) {
                        return
                    }
                    translate(distanceX)
                }

                override fun onActionUp() {
                    if (!scroller.isFinished) {
                        return
                    }
                    scroller.springBack(-scrollX, 0, -(SPACE * getSpaceCount()).toInt(), 0, 0, 0)
                    onProgressChangeListener?.onStopTracking(innerProgress, scrollX, true)
                    invalidate()
                }

                override fun onScaleStart() {
                }

                override fun onMultiTouchChange(isMultiTouch: Boolean, touchCount: Int) {
                }

                override fun onSingleTap(event: MotionEvent?) {
                }

                override fun onLongPress() {
                }

                override fun onActionDown() {
                    //如果有在滚动 停止滚动
                    scroller.forceFinished(true)
                    onProgressChangeListener?.onStartTracking(innerProgress, scrollX)
                }

            })
        }
    }

    /**
     * 常规的画笔
     */
    private val normalPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL_AND_STROKE
            color = 0xff000000.toInt()
            strokeWidth = XDisplayUtil.dpToPx(0.5f)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //位移
            it.save()
            it.translate(initTranslateX, 0f)
            var spaceSize = getSpaceCount()
            for (i in 0..spaceSize) {
                it.drawLine(0f, 0f, 0f, mHeight.toFloat(), normalPaint)
                it.translate(SPACE.toFloat(), 0f)
            }
            it.restore()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onInitSize(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        onInitSize(right - left, bottom - top)
    }

    fun onInitSize(width: Int, height: Int) {
        mWidth = width
        mHeight = height
        initTranslateX = getWidth() / 2f
    }

    /**
     * 需要使用可替换左右绘制限制
     */
    fun getLimitHeight(): Int {
        return mHeight - paddingTop - paddingBottom
    }

    fun getLimitWidth(): Int {
        return mWidth - paddingLeft - paddingRight
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled || event == null) {
            return super.onTouchEvent(event)
        }
        xGestureDetector.onTouchEvent(event)
        return true
    }

    /**
     * 跟随手势位移
     */
    private fun translate(dx: Float) {
        var fixDx = dx
        if (scrollX <= 0) {
            fixDx = dx / DAMP_VALUE
        } else if (scrollX >= getSpaceCount() * SPACE) {
            fixDx = dx / DAMP_VALUE
        }
        scrollBy(fixDx.toInt(), 0)
        postInvalidate()
    }

    private var spaceIndex = 0;

    /**
     * 一个使用Scroller很重要的滑动
     */
    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(-scroller.currX, 0)
            postInvalidate()
        }
        var newFraction = calculateFraction()
        var newSpaceIndex = (newFraction * getSpaceCount()).toInt()
        if (spaceIndex != newSpaceIndex) {
            VibratorUtils.onShot(4)
            spaceIndex = newSpaceIndex
        }
        var newProgress = (newFraction * (maxProgress - minProgress)).toInt() + minProgress
        if (newProgress >= minProgress && newProgress <= maxProgress) {
            if (newProgress != innerProgress) {
                innerProgress = newProgress
                onProgressChangeListener?.onProgressChange(innerProgress, scrollX, true)
            }
        }
        super.computeScroll()
    }

    /**
     *计算进度的fraction
     */
    private fun calculateFraction(): Float {
        return scrollX / (SPACE.toFloat() * getSpaceCount())
    }

    /**
     * 计算Space分割Count
     * 分割maxProgress 和minProgress
     */
    private fun getSpaceCount(): Int {
        return (maxProgress - minProgress) / perSpaceProgress
    }

    /**
     * 通过进度计算ScrollX
     */
    private fun calculateScrollX(progress: Int): Int {
        return (progress - minProgress) * SPACE * getSpaceCount() / (maxProgress - minProgress)
    }

    override fun isHorizontalFadingEdgeEnabled(): Boolean {
        return true
    }

    override fun getLeftFadingEdgeStrength(): Float {
        return XDisplayUtil.dpToPx(150f)
    }

    override fun getRightFadingEdgeStrength(): Float {
        return XDisplayUtil.dpToPx(150f)
    }

}