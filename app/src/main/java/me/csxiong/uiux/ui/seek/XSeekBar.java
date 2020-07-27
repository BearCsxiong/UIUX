package me.csxiong.uiux.ui.seek;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import me.csxiong.library.utils.VibratorUtils;
import me.csxiong.library.utils.XAnimator;
import me.csxiong.library.utils.XAnimatorCaculateValuer;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.uiux.R;

/**
 * @Desc : 一个内容可编辑的Seekbar 比较简易
 * 添加自动滚动和中心可移动模式
 * @Author : csxiong - 2020-01-14
 * {@link #defaultPosition} SeekBar默认位置
 * {@link #centerPointPercent} SeekBar进度中心位置
 */
public class XSeekBar extends View {
    /**
     * 调整气泡的距离
     */
    public static final int FIX_DISTANCE = XDisplayUtil.dpToPxInt(5);
    /**
     * 气泡残余停留时间
     */
    public static final long BUBBLE_DURATION = 300;
    /**
     * 气泡展示时间
     */
    public static final long BUBBLE_SHOW_DURATION = 200;
    /**
     * 是否允许描边
     */
    private boolean isEnableStroke = false;
    /**
     * 是否需要中心点
     */
    private boolean isEnableCenterPoint = false;
    /**
     * 是否支持图钉中心指示器
     */
    private boolean isEnableThumbIndicator = false;
    /**
     * 背景颜色
     */
    private int backgroundColor = 0x80F2F2F2;
    /**
     * 描边颜色
     */
    private int strokeColor = 0x33000000;
    /**
     * 进度颜色
     */
    private int progressColor = 0xffffffff;
    /**
     * 图钉中心指示器颜色
     */
    private int mThumbIndicatorColor = 0xffFB5986;
    /**
     * 图钉半径
     */
    private int mThumbRadius = XDisplayUtil.dpToPxInt(9.5f);
    /**
     * 中心点宽度
     */
    private int mCenterPointWidth = XDisplayUtil.dpToPxInt(3);
    /**
     * 中心点point高度
     */
    private int mCenterPointHeight = XDisplayUtil.dpToPxInt(7);
    /**
     * bar高度
     */
    private float mSeekBarHeight = XDisplayUtil.dpToPx(2.5f);
    /**
     * 进度百分比 0~1之间的浮点数
     */
    private float progressPercent = 0f;
    /**
     * 进度浮点 为了UI渲染在progress范围小时 UI显得卡顿
     */
    private float progress = 0f;
    /**
     * 整型进度 回调进度
     */
    private int intProgress = 0;
    /**
     * 中心点位于滑杆位置
     */
    private float centerPointPercent = 0f;
    /**
     * 描边宽度
     */
    private float strokeWidth = 1;
    /**
     * 最小进度
     */
    private int minProgress = 0;
    /**
     * 最大进度
     */
    private int maxProgress = 100;
    /**
     * View宽度
     */
    private int width;
    /**
     * View高度
     */
    private int height;
    /**
     * bar宽度 -> 0-100 真实的bar宽度 背景因为兼容滑杆非圆心对称 不是真实progress的宽度
     */
    private float barWidth;
    /**
     * bar长条真实宽度
     */
    private float backgroundWidth;
    /**
     * 背景矩形
     */
    private RectF mBackgroundRectf = new RectF();
    /**
     * 背景描边矩形
     */
    private RectF mBackgroundStrokeRectf = new RectF();
    /**
     * 进度矩形
     */
    private RectF mProgressRectf = new RectF();
    /**
     * 中心点矩形
     */
    private RectF mCenterPointRectf = new RectF();
    /**
     * 中心描边矩形
     */
    private RectF mCenterStrokePointRectf = new RectF();
    /**
     * 普通颜色笔
     */
    private Paint mProgressPaint;
    /**
     * 背景笔
     */
    private Paint mBackgroundPaint;
    /**
     * 描边笔
     */
    private Paint mStrokePaint;
    /**
     * 图钉指示器画笔
     */
    private Paint mThumbIndicatorPaint;
    /**
     * 默认位置的半径
     */
    private int defaultRadius = XDisplayUtil.dpToPxInt(3);
    /**
     * 默认位置X
     */
    private float defaultPositionX;
    /**
     * 默认位置
     */
    private float defaultPosition = 0.0f;
    /**
     * 默认进度
     */
    private float defaultProgress = 0;
    /**
     * 期望进度
     */
    private float forwardProgress;
    /**
     * 是否可用
     */
    private boolean isSeekEnable = true;
    /**
     * 进度计算
     */
    private XAnimatorCaculateValuer progressValuer = new XAnimatorCaculateValuer();
    /**
     * 绘制组成部分
     */
    private ArrayList<XSeekDrawPart> drawParts = new ArrayList<>();
    /**
     * 执行动画
     */
    private XAnimator animator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(new XAnimator.XAnimationListener() {
                @Override
                public void onAnimationUpdate(float fraction, float value) {
                    setProgress((int) progressValuer.caculateValue(fraction), false);
                }

                @Override
                public void onAnimationStart(XAnimator animation) {
                    progressValuer.mark(progress,forwardProgress);
                }

                @Override
                public void onAnimationEnd(XAnimator animation) {
                    if (onProgressChangeListener != null) {
                        onProgressChangeListener.onProgressChange(intProgress, getLimitLeft() + barWidth * progressPercent,
                                false);
                    }
                }

                @Override
                public void onAnimationCancel(XAnimator animation) {
                    if (onProgressChangeListener != null) {
                        onProgressChangeListener.onProgressChange(intProgress, getLimitLeft() + barWidth * progressPercent,
                                false);
                    }
                }
            });

    public XSeekBar(Context context) {
        this(context, null);
    }

    public XSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (context != null && attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XSeekBar);
            isEnableStroke = ta.getBoolean(R.styleable.XSeekBar_isEnableStroke, false);
            isEnableCenterPoint = ta.getBoolean(R.styleable.XSeekBar_isEnableCenterPoint, false);
            isEnableThumbIndicator = ta.getBoolean(R.styleable.XSeekBar_isEnableThumbIndicator, false);
            mThumbIndicatorColor = ta.getColor(R.styleable.XSeekBar_xThumbIndicatorColor, 0xffFB5986);
            backgroundColor = ta.getColor(R.styleable.XSeekBar_xBackgroundColor, 0x80F2F2F2);
            strokeColor = ta.getColor(R.styleable.XSeekBar_xStrokeColor, 0x33000000);
            progressColor = ta.getColor(R.styleable.XSeekBar_xProgressColor, 0xffffffff);
            mThumbRadius = ta.getDimensionPixelSize(R.styleable.XSeekBar_xThumbRadius, XDisplayUtil.dpToPxInt(9.5f));
            mCenterPointWidth = ta.getDimensionPixelSize(R.styleable.XSeekBar_xCenterPointWidth, XDisplayUtil.dpToPxInt(3));
            mCenterPointHeight =
                    ta.getDimensionPixelSize(R.styleable.XSeekBar_xCenterPointHeight, XDisplayUtil.dpToPxInt(7));
            mSeekBarHeight = ta.getDimensionPixelSize(R.styleable.XSeekBar_xSeekbarHeight, XDisplayUtil.dpToPxInt(2.5f));
            centerPointPercent = ta.getFloat(R.styleable.XSeekBar_xCenterPointPercent, 0f);
            maxProgress = ta.getInteger(R.styleable.XSeekBar_xMaxProgress, 100);
            minProgress = ta.getInteger(R.styleable.XSeekBar_xMinProgress, 0);
            progress = ta.getInteger(R.styleable.XSeekBar_xProgress, 0);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStrokeWidth(strokeWidth);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mThumbIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbIndicatorPaint.setColor(mThumbIndicatorColor);
        mThumbIndicatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景
        if (mBackgroundRectf != null) {
            canvas.drawRoundRect(mBackgroundRectf, 50, 50, mBackgroundPaint);
        }
        // 绘制进度
        if (mProgressRectf != null) {
            canvas.drawRoundRect(mProgressRectf, 50, 50, mProgressPaint);
        }
        // 绘制描边
        if (isEnableStroke && mBackgroundStrokeRectf != null) {
            canvas.drawRoundRect(mBackgroundStrokeRectf, 50, 50, mStrokePaint);
        }
        // 绘制中心点
        if (isEnableCenterPoint && mCenterPointRectf != null) {
            canvas.drawRoundRect(mCenterPointRectf, 50, 50, mProgressPaint);
            if (isEnableStroke && mCenterStrokePointRectf != null) {
                // 绘制中心描边
                canvas.drawRoundRect(mCenterStrokePointRectf, 50, 50, mStrokePaint);
            }
        }
        // 默认位置点
        if (defaultPosition != 0) {
            // 绘制默认点
            this.defaultPositionX = barWidth * defaultPosition + getPaddingLeft() + (1 + defaultPosition) * mThumbRadius
                    + strokeWidth * 2 - defaultRadius;
            canvas.drawCircle(defaultPositionX, height / 2f, defaultRadius, mProgressPaint);
            if (isEnableStroke) {
                canvas.drawCircle(defaultPositionX, height / 2f, defaultRadius + strokeWidth, mStrokePaint);
            }
        }
        // 绘制手指拖动的thumb
        canvas.drawCircle(getLimitLeft() + barWidth * progressPercent, height / 2f, mThumbRadius, mProgressPaint);
        if (isEnableStroke) {
            // 描边
            canvas.drawCircle(getLimitLeft() + barWidth * progressPercent, height / 2f, mThumbRadius + strokeWidth,
                    mStrokePaint);
        }
        // 绘制手指拖动的thumb中的indicator标记点
        if (isEnableThumbIndicator) {
            canvas.drawCircle(getLimitLeft() + barWidth * progressPercent, height / 2f, XDisplayUtil.dpToPxInt(2.5f),
                    mThumbIndicatorPaint);
        }
    }

    /**
     * 设置默认位置
     *
     * @param defaultPosition
     */
    public void setDefaultPosition(@FloatRange(from = 0.0f, to = 1.0f)float defaultPosition) {
        // 计算推荐值的位置问题 使用真实计算bar长 但是又要以右为终点
        this.defaultPosition = defaultPosition;
        this.defaultProgress = defaultPosition * (maxProgress - minProgress);
    }

    /**
     * 设置中心比例
     *
     * @param centerPointPercent
     */
    public void setCenterPointPercent(@FloatRange(from = 0.0f, to = 1.0f) float centerPointPercent) {
        this.centerPointPercent = centerPointPercent;
        // 设置中心的Rectf
        mCenterPointRectf.set(getLimitLeft() + barWidth * centerPointPercent - mCenterPointWidth / 2f,
                height / 2f - mCenterPointHeight / 2f,
                getLimitLeft() + barWidth * centerPointPercent + mCenterPointWidth / 2f,
                height / 2f + mCenterPointHeight / 2f);
        mCenterStrokePointRectf.set(mCenterPointRectf.left - strokeWidth, mCenterPointRectf.top - strokeWidth,
                mCenterPointRectf.right + strokeWidth, mCenterPointRectf.bottom + strokeWidth);

        invalidate();
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        setProgress(progress, false);
    }

    /**
     * 设置进度
     *
     * @param progress
     * @param withAnimation
     */
    public void setProgress(int progress, boolean withAnimation) {
        boolean isChange = progress != intProgress;
        setProgress(progress, false, withAnimation);
        // 手动设置也是需要回调的
        // 若执行动画 会在动画执行之后 回调结果
        if (isChange && !withAnimation && onProgressChangeListener != null) {
            onProgressChangeListener.onProgressChange(intProgress, getLimitLeft() + barWidth * progressPercent, false);
        }
    }

    /**
     * 设置进度
     *
     * @param progress      期望进度
     * @param isReset       是否重置设置
     * @param withAnimation 是否执行动画
     */
    private void setProgress(float progress, boolean isReset, boolean withAnimation) {
        if (isReset || (progress >= minProgress && progress <= maxProgress)) {
            if (withAnimation) {
                forwardProgress = progress;
                animator.cancel();
                animator.start();
            } else {
                this.progress = progress;
                this.intProgress = (int) progress;
                progressPercent = progress / (float) (maxProgress - minProgress) + centerPointPercent;
                float left = progressPercent > centerPointPercent
                        ? getPaddingLeft() + strokeWidth * 2 + centerPointPercent * backgroundWidth
                        : backgroundWidth * progressPercent + getPaddingLeft() + strokeWidth * 2;
                float right = progressPercent > centerPointPercent
                        ? backgroundWidth * progressPercent + getPaddingRight() + strokeWidth * 2
                        : getPaddingRight() + strokeWidth * 2 + centerPointPercent * backgroundWidth;
                mProgressRectf.set(left, height / 2f - mSeekBarHeight / 2f, right, height / 2f + mSeekBarHeight / 2f);
                invalidate();
            }
        }

    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        if (mBackgroundPaint != null) {
            mBackgroundPaint.setColor(backgroundColor);
        }
        invalidate();
    }

    /**
     * 设置进度颜色
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        if (mProgressPaint != null) {
            mProgressPaint.setColor(progressColor);
        }
        invalidate();
    }

    public void setThumbIndicatorColor(int mThumbIndicatorColor) {
        this.mThumbIndicatorColor = mThumbIndicatorColor;
        mThumbIndicatorPaint.setColor(mThumbIndicatorColor);
        invalidate();
    }

    /**
     * 设置描边颜色
     *
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        if (mStrokePaint != null) {
            mStrokePaint.setColor(strokeColor);
        }
        invalidate();
    }

    /**
     * 是否绘制中心点
     *
     * @param enableCenterPoint
     */
    public void setEnableCenterPoint(boolean enableCenterPoint) {
        isEnableCenterPoint = enableCenterPoint;
        invalidate();
    }

    /**
     * 设置是否描边
     *
     * @param enableStroke
     */
    public void setEnableStroke(boolean enableStroke) {
        isEnableStroke = enableStroke;
        invalidate();
    }

    /**
     * 设置最小进度
     *
     * @param minProgress
     */
    public void setMinProgress(int minProgress) {
        this.minProgress = minProgress;
        invalidate();
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initSize(right - left, bottom - top);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(w, h);
    }

    public int getProgress() {
        return intProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getMinProgress() {
        return minProgress;
    }

    /**
     * 是否触控
     */
    private boolean isTouch;
    /**
     * 进度改变监听
     */
    private OnProgressChangeListener onProgressChangeListener;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !isSeekEnable) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        progressPercent = getTouchPercent(event);
        // boolean isSeekLeft = isSeekLeft(event);
        if (progressPercent < 0) {
            progressPercent = 0;
        } else if (progressPercent > 1) {
            progressPercent = 1;
        }
        // 计算新进度
        float newProgress = (progressPercent - centerPointPercent) * (maxProgress - minProgress);
        if (action == MotionEvent.ACTION_DOWN) {
            isTouch = true;
            setProgress(newProgress, true, false);
            if (onProgressChangeListener != null) {
                onProgressChangeListener.onStartTracking(intProgress, getLimitLeft() + barWidth * progressPercent);
            }
            return true;
        } else if (isTouch && action == MotionEvent.ACTION_MOVE) {
            // 自动吸附功能
            // 中心点和默认推荐值 均需要做自动吸附
            // 默认推荐值吸附
            if (defaultPosition != 0.0f) {
                if (newProgress >= defaultProgress - 3 && newProgress <= defaultProgress + 3) {
                    newProgress = defaultProgress;
                }
            }
            // 中心点吸附
            if (isEnableCenterPoint) {
                if (newProgress >= -3 && newProgress <= 3) {
                    newProgress = 0;
                }
            }
            int intNewProgress = (int) newProgress;
            boolean isChange = intNewProgress != intProgress;
            setProgress(newProgress, false, false);
            if (isChange) {
                if (onProgressChangeListener != null) {
                    if (defaultPosition != 0.0f) {
                        if (newProgress == defaultProgress) {
                            VibratorUtils.onShot(30);
                        }
                    }
                    if (isEnableCenterPoint) {
                        if (newProgress == 0) {
                            VibratorUtils.onShot(30);
                        }
                    }
                    onProgressChangeListener.onProgressChange(intProgress, getLimitLeft() + barWidth * progressPercent,
                            true);
                }
            }
            if (onProgressChangeListener != null) {
                onProgressChangeListener.onPositionChange(intProgress, getLimitLeft() + barWidth * progressPercent);
            }
        } else if (isTouch && action == MotionEvent.ACTION_UP) {
            // setProgress(newProgress, true, false);
            isTouch = false;
            if (onProgressChangeListener != null) {
                onProgressChangeListener.onProgressChange(intProgress, getLimitLeft() + barWidth * progressPercent,
                        true);
                onProgressChangeListener.onStopTracking(intProgress, getLimitLeft() + barWidth * progressPercent, true);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取接触百分比
     * 以左向右作为基准
     * 均使用百分比触控计算所有值  因为这样有利控制原点和其他参数的关系
     *
     * @param event
     * @return
     */
    private float getTouchPercent(MotionEvent event) {
        float x = event.getX() - getLimitLeft();
        return x / barWidth;
    }

    /**
     * 适配ViewPadding
     *
     * @return
     */
    private float getLimitLeft() {
        return getPaddingLeft() + mThumbRadius + strokeWidth * 2;
    }

    /**
     * 适配ViewPadding
     *
     * @return
     */
    private float getLimitRight() {
        return getPaddingRight() + mThumbRadius + strokeWidth * 2;
    }

    /**
     * 初始化size
     */
    private void initSize(int width, int height) {
        this.width = width;
        this.height = height;
        barWidth = width - getLimitLeft() - getLimitRight();
        backgroundWidth = width - getPaddingLeft() - getPaddingRight() - strokeWidth * 4;
        // 这边背景rect不是用LimitLeft or LimitRight是因为 滑杆头尾不以thumb的圆心为中心
        // 初始化基础图形结构
        mBackgroundRectf.set(getPaddingLeft() + strokeWidth * 2, height / 2f - mSeekBarHeight / 2f,
                width - getPaddingRight() - strokeWidth * 2, height / 2f + mSeekBarHeight / 2f);
        // 背景描边
        mBackgroundStrokeRectf.set(mBackgroundRectf.left - strokeWidth, mBackgroundRectf.top - strokeWidth,
                mBackgroundRectf.right + strokeWidth, mBackgroundRectf.bottom + strokeWidth);
        // 进度基础Rectf
        setProgress(progress, true, false);
        // 设置中心点比例
        setCenterPointPercent(centerPointPercent);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    /**
     * 进度改变监听
     */
    public interface OnProgressChangeListener {

        /**
         * 开始拖动
         *
         * @param progress
         */
        default void onStartTracking(int progress, float leftDx) {
        }

        /**
         * 进度改变
         *
         * @param progress
         * @param fromUser
         */
        default void onProgressChange(int progress, float leftDx, boolean fromUser) {
        }

        /**
         * 位置改变监听
         *
         * @param leftDx
         */
        default void onPositionChange(int progress, float leftDx) {
        }

        /**
         * 停止拖动
         *
         * @param progress
         */
        default void onStopTracking(int progress, float leftDx, boolean fromUser) {
        }
    }

    public void setSeekEnable(boolean seekEnable) {
        isSeekEnable = seekEnable;
        if (isSeekEnable) {
            setAlpha(1.0f);
        } else {
            setAlpha(0.5f);
        }
    }

}
