package me.csxiong.uiux.ui.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.csxiong.library.utils.XAnimator;

/**
 * @Desc : 渐变View
 * @Author : Bear - 2020/6/9
 */
public class GradientView extends View {

    private Paint mPaint;
    /**
     * 渐变
     */
    private LinearGradient mLinearGradient;
    /**
     * 可视范围
     */
    private Rect mVisibleRect = new Rect();
    /**
     * 最大绘制渐变区域长度
     */
    private Rect mFullRect = new Rect();
    /**
     * 最大渐变长度
     */
    private int fullDistance;
    /**
     * 滚动距离
     */
    private int scrollDistance;
    /**
     * 是否倒播
     */
    private boolean isReverse = false;
    /**
     * 画布位移
     */
    private int translateX;
    /**
     * 是否销毁
     */
    private boolean isDestory = false;
    /**
     * 动画回调
     */
    public XAnimator mAnimator = XAnimator.ofFloat(0f, 1f)
            .duration(3000)
            .setAnimationListener(new XAnimator.XAnimationListener() {
                @Override
                public void onAnimationUpdate(float fraction, float value) {
                    if (isReverse) {
                        translateX = (int) (scrollDistance * (1f - fraction));
                    } else {
                        translateX = (int) (scrollDistance * fraction);
                    }
                    invalidate();
                }

                @Override
                public void onAnimationStart(XAnimator animation) {

                }

                @Override
                public void onAnimationEnd(XAnimator animation) {
                    if (!isDestory) {
                        isReverse = !isReverse;
                        mAnimator.cancel();
                        mAnimator.start();
                    }
                }

                @Override
                public void onAnimationCancel(XAnimator animation) {

                }
            });

    public GradientView(Context context) {
        super(context);
        init();
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(-translateX, 0);
        canvas.drawRect(mFullRect, mPaint);
        canvas.restore();
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

    private int w;
    private int h;

    /**
     * 初始化Size
     *
     * @param w
     * @param h
     */
    public void initSize(int w, int h) {
        this.w = w;
        this.h = h;
        fullDistance = (int) (w * 2.5f);
        scrollDistance = fullDistance - w;
        mVisibleRect.set(0, 0, w, h);
        mFullRect.set(0, 0, fullDistance, h);
        mLinearGradient = new LinearGradient(0, 0, fullDistance, h * 2, new int[]{0xFFFFC75C, 0xFFFFAB6C, 0xFFFF87F7, 0xFFB397FE, 0xFFFF88F2, 0xFFFFC55E},
                new float[]{0.0f, 0.17f, 0.39f, 0.59f, 0.81f, 1.0f},
                Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    public void start() {
        isDestory = false;
        mAnimator.start();
    }

    public void destroy() {
        isDestory = true;
        mAnimator.cancel();
    }
}
