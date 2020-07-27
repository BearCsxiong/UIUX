package me.csxiong.uiux.ui.seek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import me.csxiong.library.utils.VibratorUtils
import me.csxiong.library.utils.XAnimator
import me.csxiong.library.utils.XAnimator.XAnimationListener
import me.csxiong.library.utils.XAnimatorCaculateValuer
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.R
import me.csxiong.uiux.ui.seek.part.*

/**
 * @Desc : 一个内容可编辑的Seekbar 比较简易
 * 添加自动滚动和中心可移动模式
 * @Author : csxiong - 2020-01-14
 * [defaultPosition] SeekBar默认位置
 * [centerPointPercent] SeekBar进度中心位置
 */
class XSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    /**
     * 是否允许描边
     */
    var isEnableStroke = false
    /**
     * 是否需要中心点
     */
    var isEnableCenterPoint = false
    /**
     * 是否支持图钉中心指示器
     */
    var isEnableThumbIndicator = false
    /**
     * 背景颜色
     */
    var mBackgroundColor = -0x7f0d0d0e
    /**
     * 描边颜色
     */
    var strokeColor = 0x33000000
    /**
     * 进度颜色
     */
    var progressColor = -0x1
    /**
     * 图钉中心指示器颜色
     */
    var mThumbIndicatorColor = -0x4a67a
    /**
     * 图钉半径
     */
    var mThumbRadius = XDisplayUtil.dpToPxInt(9.5f)
    /**
     * 中心点宽度
     */
    var mCenterPointWidth = XDisplayUtil.dpToPxInt(3f)
    /**
     * 中心点point高度
     */
    var mCenterPointHeight = XDisplayUtil.dpToPxInt(7f)
    /**
     * bar高度
     */
    var mSeekBarHeight = XDisplayUtil.dpToPx(2.5f)
    /**
     * 进度百分比 0~1之间的浮点数
     */
    var progressPercent = 0f
    /**
     * 进度浮点 为了UI渲染在progress范围小时 UI显得卡顿
     */
    var progress = 0f
    /**
     * 整型进度 回调进度
     */
    var intProgress = 0
    /**
     * 中心点位于滑杆位置
     */
    var centerPointPercent = 0f
    /**
     * 描边宽度
     */
    val strokeWidth = 1f
    /**
     * 最小进度
     */
    var minProgress = 0
    /**
     * 最大进度
     */
    var maxProgress = 100
    /**
     * View宽度
     */
    var customWidth = 0
    /**
     * View高度
     */
    var customHeight = 0
    /**
     * bar宽度 -> 0-100 真实的bar宽度 背景因为兼容滑杆非圆心对称 不是真实progress的宽度
     */
    var barWidth = 0f
    /**
     * bar长条真实宽度
     */
    var backgroundWidth = 0f
    /**
     * 背景矩形
     */
    val mBackgroundRectf: RectF? = RectF()
    /**
     * 背景描边矩形
     */
    val mBackgroundStrokeRectf: RectF? = RectF()
    /**
     * 进度矩形
     */
    val mProgressRectf: RectF = RectF()
    /**
     * 普通颜色笔
     */
    var mProgressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 背景笔
     */
    var mBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 描边笔
     */
    var mStrokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 图钉指示器画笔
     */
    var mThumbIndicatorPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 默认位置的半径
     */
    val defaultRadius = XDisplayUtil.dpToPxInt(3f)
    /**
     * 默认位置X
     */
    var defaultPositionX = 0f
    /**
     * 默认位置
     */
    var defaultPosition = 0.0f
    /**
     * 默认进度
     */
    var defaultProgress = 0f
    /**
     * 期望进度
     */
    var forwardProgress = 0f
    /**
     * 是否可用
     */
    private var isSeekEnable = true
    /**
     * 进度计算
     */
    val progressValuer = XAnimatorCaculateValuer()

    val backgroundPart = XSeekBackgroundPart(this)
    val progressPart = XSeekProgressPart(this)
    val centerPositionPart = XSeekCenterPositionPart(this)
    val defaultPositionPart = XSeekDefaultPositionPart(this)
    val thumbPart = XSeekThumbPart(this)
    val thumbIndicatorPart = XSeekThumbIndicatorPart(this)
    /**
     * 执行动画
     */
    private val animator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(object : XAnimationListener {
                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    setProgress(progressValuer.caculateValue(fraction).toInt(), false)
                }

                override fun onAnimationStart(animation: XAnimator) {
                    progressValuer.mark(progress, forwardProgress)
                }

                override fun onAnimationEnd(animation: XAnimator) {
                    if (onProgressChangeListener != null) {
                        onProgressChangeListener!!.onProgressChange(intProgress, limitLeft + barWidth * progressPercent,
                                false)
                    }
                }

                override fun onAnimationCancel(animation: XAnimator) {
                    if (onProgressChangeListener != null) {
                        onProgressChangeListener!!.onProgressChange(intProgress, limitLeft + barWidth * progressPercent,
                                false)
                    }
                }
            })

    private fun initAttrs(context: Context?, attrs: AttributeSet?) {
        if (context != null && attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.XSeekBar)
            isEnableStroke = ta.getBoolean(R.styleable.XSeekBar_isEnableStroke, false)
            isEnableCenterPoint = ta.getBoolean(R.styleable.XSeekBar_isEnableCenterPoint, false)
            isEnableThumbIndicator = ta.getBoolean(R.styleable.XSeekBar_isEnableThumbIndicator, false)
            mThumbIndicatorColor = ta.getColor(R.styleable.XSeekBar_xThumbIndicatorColor, -0x4a67a)
            mBackgroundColor = ta.getColor(R.styleable.XSeekBar_xBackgroundColor, -0x7f0d0d0e)
            strokeColor = ta.getColor(R.styleable.XSeekBar_xStrokeColor, 0x33000000)
            progressColor = ta.getColor(R.styleable.XSeekBar_xProgressColor, -0x1)
            mThumbRadius = ta.getDimensionPixelSize(R.styleable.XSeekBar_xThumbRadius, XDisplayUtil.dpToPxInt(9.5f))
            mCenterPointWidth = ta.getDimensionPixelSize(R.styleable.XSeekBar_xCenterPointWidth, XDisplayUtil.dpToPxInt(3f))
            mCenterPointHeight = ta.getDimensionPixelSize(R.styleable.XSeekBar_xCenterPointHeight, XDisplayUtil.dpToPxInt(7f))
            mSeekBarHeight = ta.getDimensionPixelSize(R.styleable.XSeekBar_xSeekbarHeight, XDisplayUtil.dpToPxInt(2.5f)).toFloat()
            centerPointPercent = ta.getFloat(R.styleable.XSeekBar_xCenterPointPercent, 0f)
            maxProgress = ta.getInteger(R.styleable.XSeekBar_xMaxProgress, 100)
            minProgress = ta.getInteger(R.styleable.XSeekBar_xMinProgress, 0)
            progress = ta.getInteger(R.styleable.XSeekBar_xProgress, 0).toFloat()
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        initAttrs(context, attrs)
        mBackgroundPaint!!.color = mBackgroundColor
        mBackgroundPaint!!.style = Paint.Style.FILL_AND_STROKE
        mStrokePaint!!.color = strokeColor
        mStrokePaint!!.strokeWidth = strokeWidth
        mStrokePaint!!.style = Paint.Style.STROKE
        mProgressPaint!!.color = progressColor
        mProgressPaint!!.style = Paint.Style.FILL_AND_STROKE
        mThumbIndicatorPaint!!.color = mThumbIndicatorColor
        mThumbIndicatorPaint!!.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        backgroundPart.onDraw(canvas)
        progressPart.onDraw(canvas)
        centerPositionPart.onDraw(canvas)
        defaultPositionPart.onDraw(canvas)
        thumbPart.onDraw(canvas)
        thumbIndicatorPart.onDraw(canvas)
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    fun setProgress(progress: Int) {
        setProgress(progress, false)
    }

    /**
     * 设置进度
     *
     * @param progress
     * @param withAnimation
     */
    fun setProgress(progress: Int, withAnimation: Boolean) {
        val isChange = progress != intProgress
        setProgress(progress.toFloat(), false, withAnimation)
        // 手动设置也是需要回调的
        // 若执行动画 会在动画执行之后 回调结果
        if (isChange && !withAnimation && onProgressChangeListener != null) {
            onProgressChangeListener!!.onProgressChange(intProgress, limitLeft + barWidth * progressPercent, false)
        }
    }

    /**
     * 设置进度
     *
     * @param progress      期望进度
     * @param isReset       是否重置设置
     * @param withAnimation 是否执行动画
     */
    private fun setProgress(progress: Float, isReset: Boolean, withAnimation: Boolean) {
        if (isReset || progress >= minProgress && progress <= maxProgress) {
            if (withAnimation) {
                forwardProgress = progress
                animator.cancel()
                animator.start()
            } else {
                this.progress = progress
                intProgress = progress.toInt()
                progressPercent = progress / (maxProgress - minProgress).toFloat() + centerPointPercent
                val left = if (progressPercent > centerPointPercent) paddingLeft + strokeWidth * 2 + centerPointPercent * backgroundWidth else backgroundWidth * progressPercent + paddingLeft + strokeWidth * 2
                val right = if (progressPercent > centerPointPercent) backgroundWidth * progressPercent + paddingRight + strokeWidth * 2 else paddingRight + strokeWidth * 2 + centerPointPercent * backgroundWidth
                mProgressRectf!![left, customHeight / 2f - mSeekBarHeight / 2f, right] = customHeight / 2f + mSeekBarHeight / 2f
                invalidate()
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initSize(right - left, bottom - top)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSize(w, h)
    }

    /**
     * 是否触控
     */
    private var isTouch = false
    /**
     * 进度改变监听
     */
    private var onProgressChangeListener: OnProgressChangeListener? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled || !isSeekEnable) {
            return super.onTouchEvent(event)
        }
        val action = event.action
        progressPercent = getTouchPercent(event)
        // boolean isSeekLeft = isSeekLeft(event);
        if (progressPercent < 0) {
            progressPercent = 0f
        } else if (progressPercent > 1) {
            progressPercent = 1f
        }
        // 计算新进度
        var newProgress = (progressPercent - centerPointPercent) * (maxProgress - minProgress)
        if (action == MotionEvent.ACTION_DOWN) {
            isTouch = true
            setProgress(newProgress, true, false)
            if (onProgressChangeListener != null) {
                onProgressChangeListener!!.onStartTracking(intProgress, limitLeft + barWidth * progressPercent)
            }
            return true
        } else if (isTouch && action == MotionEvent.ACTION_MOVE) { // 自动吸附功能
// 中心点和默认推荐值 均需要做自动吸附
// 默认推荐值吸附
            if (defaultPosition != 0.0f) {
                if (newProgress >= defaultProgress - 3 && newProgress <= defaultProgress + 3) {
                    newProgress = defaultProgress
                }
            }
            // 中心点吸附
            if (isEnableCenterPoint) {
                if (newProgress >= -3 && newProgress <= 3) {
                    newProgress = 0f
                }
            }
            val intNewProgress = newProgress.toInt()
            val isChange = intNewProgress != intProgress
            setProgress(newProgress, false, false)
            if (isChange) {
                if (onProgressChangeListener != null) {
                    if (defaultPosition != 0.0f) {
                        if (newProgress == defaultProgress) {
                            VibratorUtils.onShot(30)
                        }
                    }
                    if (isEnableCenterPoint) {
                        if (newProgress == 0f) {
                            VibratorUtils.onShot(30)
                        }
                    }
                    onProgressChangeListener!!.onProgressChange(intProgress, limitLeft + barWidth * progressPercent,
                            true)
                }
            }
            if (onProgressChangeListener != null) {
                onProgressChangeListener!!.onPositionChange(intProgress, limitLeft + barWidth * progressPercent)
            }
        } else if (isTouch && action == MotionEvent.ACTION_UP) { // setProgress(newProgress, true, false);
            isTouch = false
            if (onProgressChangeListener != null) {
                onProgressChangeListener!!.onProgressChange(intProgress, limitLeft + barWidth * progressPercent,
                        true)
                onProgressChangeListener!!.onStopTracking(intProgress, limitLeft + barWidth * progressPercent, true)
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 获取接触百分比
     * 以左向右作为基准
     * 均使用百分比触控计算所有值  因为这样有利控制原点和其他参数的关系
     *
     * @param event
     * @return
     */
    private fun getTouchPercent(event: MotionEvent): Float {
        val x = event.x - limitLeft
        return x / barWidth
    }

    /**
     * 适配ViewPadding
     *
     * @return
     */
    val limitLeft: Float
        get() = paddingLeft + mThumbRadius + strokeWidth * 2

    /**
     * 适配ViewPadding
     *
     * @return
     */
    val limitRight: Float
        get() = paddingRight + mThumbRadius + strokeWidth * 2

    /**
     * 初始化size
     */
    private fun initSize(width: Int, height: Int) {
        this.customWidth = width
        this.customHeight = height
        barWidth = width - limitLeft - limitRight
        backgroundWidth = width - paddingLeft - paddingRight - strokeWidth * 4
        // 这边背景rect不是用LimitLeft or LimitRight是因为 滑杆头尾不以thumb的圆心为中心
// 初始化基础图形结构
        mBackgroundRectf!![paddingLeft + strokeWidth * 2, height / 2f - mSeekBarHeight / 2f, width - paddingRight - strokeWidth * 2] = height / 2f + mSeekBarHeight / 2f
        // 背景描边
        mBackgroundStrokeRectf!![mBackgroundRectf.left - strokeWidth, mBackgroundRectf.top - strokeWidth, mBackgroundRectf.right + strokeWidth] = mBackgroundRectf.bottom + strokeWidth
        // 进度基础Rectf
        setProgress(progress, true, false)
        // 设置中心点比例
        centerPositionPart.setCenterPointPercent(centerPointPercent)
    }

    fun setOnProgressChangeListener(onProgressChangeListener: OnProgressChangeListener?) {
        this.onProgressChangeListener = onProgressChangeListener
    }

    /**
     * 进度改变监听
     */
    interface OnProgressChangeListener {
        /**
         * 开始拖动
         *
         * @param progress
         */
        fun onStartTracking(progress: Int, leftDx: Float) {}

        /**
         * 进度改变
         *
         * @param progress
         * @param fromUser
         */
        fun onProgressChange(progress: Int, leftDx: Float, fromUser: Boolean) {}

        /**
         * 位置改变监听
         *
         * @param leftDx
         */
        fun onPositionChange(progress: Int, leftDx: Float) {}

        /**
         * 停止拖动
         *
         * @param progress
         */
        fun onStopTracking(progress: Int, leftDx: Float, fromUser: Boolean) {}
    }

    fun setSeekEnable(seekEnable: Boolean) {
        isSeekEnable = seekEnable
        alpha = if (isSeekEnable) {
            1.0f
        } else {
            0.5f
        }
    }

    companion object {
        /**
         * 调整气泡的距离
         */
        val FIX_DISTANCE = XDisplayUtil.dpToPxInt(5f)
        /**
         * 气泡残余停留时间
         */
        const val BUBBLE_DURATION: Long = 300
        /**
         * 气泡展示时间
         */
        const val BUBBLE_SHOW_DURATION: Long = 200
    }

    init {
        init(context, attrs)
    }
}