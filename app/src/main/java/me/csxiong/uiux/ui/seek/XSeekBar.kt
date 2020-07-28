package me.csxiong.uiux.ui.seek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
    var isEnableStroke = true
    /**
     * 是否需要中心点
     */
    var isEnableCenterPoint = false
    /**
     * 是否支持图钉中心指示器
     */
    var isEnableThumbIndicator = false
    /**
     * 是否支持自动吸附
     */
    var isEnableAutoAdsorbPosition = true
    /**
     * 是否支持扩张模式
     */
    var isEnableExpandMode = false
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
//    var mThumbIndicatorColor = -0x4a67a
    var mThumbIndicatorColor = Color.WHITE
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
     * bar内容高度 可变
     */
    var contentHeight = mSeekBarHeight
    /**
     * 扩展的高度
     */
    var mExpandHeight = XDisplayUtil.dpToPx(15f)
    /**
     * 进度坐标X
     */
    var progressX = 0f
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
     * 中心点X坐标
     */
    var centerPointPositionX = 0f
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
    var progressStartX = 0f
    var progressEndX = 0f
    /**
     * bar长条真实宽度
     */
    var backgroundWidth = 0f
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
    var defaultProgress = 0
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

    val drawParts = ArrayList<XSeekDrawPart>()
            .apply {
                add(XSeekBackgroundPart(this@XSeekBar))
                add(XSeekProgressPart(this@XSeekBar))
                add(XSeekExpandProgressPart(this@XSeekBar))
                add(XSeekCenterPositionPart(this@XSeekBar))
                add(XSeekDefaultPositionPart(this@XSeekBar))
                add(XSeekThumbPart(this@XSeekBar))
                add(XSeekThumbIndicatorPart(this@XSeekBar))
            }

    /**
     * Progress执行动画
     */
    private val progressAnimator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(object : XAnimationListener {
                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    var curProgress = progressValuer.caculateValue(fraction)
                    progressX = calculateProgressX(curProgress)
                    setProgressInner(curProgress, false)
                }

                override fun onAnimationStart(animation: XAnimator) {
                    progressValuer.mark(progress, forwardProgress)
                }

                override fun onAnimationEnd(animation: XAnimator) {
                    onProgressChangeListener?.onProgressChange(intProgress, progressX, false)
                }

                override fun onAnimationCancel(animation: XAnimator) {
                    onProgressChangeListener?.onProgressChange(intProgress, progressX, false)
                }
            })

    var isExpand = false

    var contentHeightValuer = XAnimatorCaculateValuer()
    /**
     * 滑杆内容扩展动画
     */
    val expandAnimator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(object : XAnimator.XAnimationListener {
                override fun onAnimationEnd(animation: XAnimator?) {
                }

                override fun onAnimationCancel(animation: XAnimator?) {
                }

                override fun onAnimationStart(animation: XAnimator?) {
                }

                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    contentHeight = contentHeightValuer.caculateValue(fraction)
                    for (drawPart in drawParts) {
                        drawPart.calculateDrawValue(false)
                    }
                    invalidate()
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
            contentHeight = mSeekBarHeight
            centerPointPercent = ta.getFloat(R.styleable.XSeekBar_xCenterPointPercent, 0f)
            maxProgress = ta.getInteger(R.styleable.XSeekBar_xMaxProgress, 100)
            minProgress = ta.getInteger(R.styleable.XSeekBar_xMinProgress, 0)
            progress = ta.getInteger(R.styleable.XSeekBar_xProgress, 0).toFloat()
            ta.recycle()
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        initAttrs(context, attrs)
        mBackgroundPaint.color = mBackgroundColor
        mBackgroundPaint.style = Paint.Style.FILL
        mStrokePaint.color = strokeColor
        mStrokePaint.strokeWidth = strokeWidth
        mStrokePaint.style = Paint.Style.STROKE
        mProgressPaint.color = progressColor
        mProgressPaint.style = Paint.Style.FILL
        mThumbIndicatorPaint.color = mThumbIndicatorColor
        mThumbIndicatorPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (drawPart in drawParts) {
            drawPart.onDraw(canvas)
        }
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
        if (withAnimation) {
            forwardProgress = progress.toFloat()
            progressAnimator.cancel()
            progressAnimator.start()
        } else {
            setProgressInner(progress.toFloat(), false)
        }
    }

    /**
     * 内部设置进度
     *
     * @param progress 期望进度
     */
    private fun setProgressInner(progress: Float, fromUser: Boolean) {
        //安全范围
        when {
            progress < minProgress -> this.progress = minProgress.toFloat()
            progress > maxProgress -> this.progress = maxProgress.toFloat()
            else -> this.progress = progress
        }
        //校准Progress
        var newIntProgress = if (this.progress < 0) (this.progress - .1f).toInt() else (this.progress + .1f).toInt()
        if (intProgress != newIntProgress) {
            intProgress = newIntProgress
            onProgressChangeListener?.onProgressChange(intProgress, progressX, fromUser)
        }
        //基础的回调
        onProgressChangeListener?.onPositionChange(intProgress, progressX)
        //内部回调
        for (drawPart in drawParts) {
            drawPart.calculateDrawValue(fromUser)
        }
        //刷新
        invalidate()
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
     * 进度改变监听
     */
    private var onProgressChangeListener: OnProgressChangeListener? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled || !isSeekEnable) {
            return super.onTouchEvent(event)
        }
        val action = event.action
        //计算手势触控进度百分比
        progressX = event.x
        when {
            progressX < progressStartX -> progressX = progressStartX
            progressX > progressEndX -> progressX = progressEndX
        }
        // 计算新进度
        var newProgress = (progressX - limitLeft) * (maxProgress - minProgress) / barWidth + minProgress
        var newIntProgress: Int = newProgress.toInt()
        //支持中心吸附
        if (isEnableCenterPoint && isEnableAutoAdsorbPosition) {
            if (newIntProgress >= -FIXED_VALUE && newIntProgress <= FIXED_VALUE) {
                progressX = calculateProgressX(0f)
                newProgress = 0f
                newIntProgress = 0
            }
        }
        //支持默认值吸附
        if (defaultProgress != 0 && isEnableAutoAdsorbPosition) {
            if (newIntProgress >= defaultProgress - FIXED_VALUE && newIntProgress <= defaultProgress + FIXED_VALUE) {
                progressX = calculateProgressX(defaultProgress.toFloat())
                newProgress = defaultProgress.toFloat()
                newIntProgress = defaultProgress
            }
        }
        //判断Int级别的数值是否改变
        //浮点数progress只是为了滑动顺畅使用
        var isChange = newIntProgress != intProgress
        //如果自动吸附 需要自动吸附 + 震动
        if (isEnableAutoAdsorbPosition && isChange
                && ((defaultProgress != 0 && newIntProgress == defaultProgress)
                        || (isEnableCenterPoint && newIntProgress == 0))) {
            VibratorUtils.onShot(30)
        }
        setProgressInner(newProgress, true)
//        "${progressPercent} -- ${newProgress} -- ${newIntProgress}".print("csx")
        //对应发生变化
        if (action == MotionEvent.ACTION_DOWN) {
            onProgressChangeListener?.onStartTracking(intProgress, progressX)
            return true
        } else if (action == MotionEvent.ACTION_MOVE) { // 自动吸附功能
            if (isChange) {
                onProgressChangeListener?.onProgressChange(intProgress, progressX, true)
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) { // setProgress(newProgress, true, false);
            onProgressChangeListener?.onProgressChange(intProgress, progressX, true)
            onProgressChangeListener?.onStopTracking(intProgress, progressX, true)
        }
        return super.onTouchEvent(event)
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
        progressStartX = limitLeft
        progressEndX = width - limitRight
        progressX = calculateProgressX(progress)
        centerPointPositionX = calculateProgressX(0f)
        backgroundWidth = width - paddingLeft - paddingRight - strokeWidth * 4
        contentHeight = mSeekBarHeight
        // 这边背景rect不是用LimitLeft or LimitRight是因为 滑杆头尾不以thumb的圆心为中心
        // 进度基础Rectf
        setProgressInner(progress, false)
        for (drawPart in drawParts) {
            drawPart.calculateDrawValue(false)
        }
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

    /**
     * 计算获得正确的ProgressX
     */
    fun calculateProgressX(progress: Float): Float {
        var curProgressX = (progress - minProgress) * barWidth / (maxProgress - minProgress) + limitLeft
        when {
            curProgressX < progressStartX -> curProgressX = progressStartX
            curProgressX > progressEndX -> curProgressX = progressEndX
        }
        return curProgressX
    }

    fun calculateProgressPercent(progress: Float): Float {
        return (progress - minProgress) / (maxProgress - minProgress)
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
        /**
         * 合并值
         */
        const val FIXED_VALUE = 2
    }

    fun expand(isExpand: Boolean) {
        if (!isEnableExpandMode || this.isExpand == isExpand) {
            return
        }
        this.isExpand = isExpand
        if (this.isExpand) {
            contentHeightValuer.mark(contentHeight, mExpandHeight)
        } else {
            contentHeightValuer.mark(contentHeight, mSeekBarHeight + strokeWidth * 2)
        }
        expandAnimator.cancel()
        expandAnimator.start()
    }

    init {
        init(context, attrs)
    }
}