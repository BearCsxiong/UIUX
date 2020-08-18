package me.csxiong.uiux.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 支持阻尼回弹的"横向"布局控件
 */
public class NestedScrollHLinearLayout extends LinearLayout implements NestedScrollingParent {
    /**
     * 除数越大可以滑动的距离越短
     */
    private static final int DRAG = 5;
    private static final int MAX_WIDTH = 200;

    private View headerView;
    private View footerView;
    private View childView;
    /**
     * 解决多点触控问题
     */
    private boolean isRunAnim;
    private boolean startLeftDrag = false;
    private int defaultScrollX = 0;

    private OnDragLeftListener onDragLeftListener;

    public NestedScrollHLinearLayout(Context context) {
        this(context, null);
    }

    public NestedScrollHLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollHLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        headerView = new View(context);
        headerView.setBackgroundColor(0xfff);
        footerView = new View(context);
        footerView.setBackgroundColor(0xfff);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setOnDragLeftListener(OnDragLeftListener listener) {
        this.onDragLeftListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childView = getChildAt(0);
        LayoutParams layoutParams = new LayoutParams(MAX_WIDTH, LayoutParams.MATCH_PARENT);
        addView(headerView, 0, layoutParams);
        addView(footerView, getChildCount(), layoutParams);
        // 左移
        scrollBy(MAX_WIDTH, 0);
        defaultScrollX = getScrollX();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = childView.getLayoutParams();
        params.width = getMeasuredWidth();
    }

    /**
     * 必须要复写 onStartNestedScroll后调用
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
    }

    /**
     * 返回true代表处理本次事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        return target instanceof RecyclerView && !isRunAnim;
    }

    /**
     * 复位初始位置
     */
    @Override
    public void onStopNestedScroll(@NonNull View target) {
        startAnimation(new ProgressAnimation());
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        // 如果在自定义ViewGroup之上还有父View交给我来处理
        getParent().requestDisallowInterceptTouchEvent(true);
        // dx>0 往左滑动 dx<0往右滑动
        boolean hiddenLeft = dx > 0 && getScrollX() < MAX_WIDTH && !target.canScrollHorizontally(-1);
        boolean showLeft = dx < 0 && !target.canScrollHorizontally(-1);
        boolean hiddenRight = dx < 0 && getScrollX() > MAX_WIDTH && !target.canScrollHorizontally(1);
        boolean showRight = dx > 0 && !target.canScrollHorizontally(1);
        if (hiddenLeft || showLeft || hiddenRight || showRight) {
            scrollBy(dx / DRAG, 0);
            consumed[0] = dx;
        }
        if (!startLeftDrag && !target.canScrollHorizontally(-1) && (defaultScrollX - getScrollX()) > 25) {
            startLeftDrag = true;
            if (onDragLeftListener != null) {
                onDragLeftListener.onDragLeft(startLeftDrag);
            }

        }
        // 限制错位问题
        if (dx > 0 && getScrollX() > MAX_WIDTH && !target.canScrollHorizontally(-1)) {
            scrollTo(MAX_WIDTH, 0);
        }
        if (dx < 0 && getScrollX() < MAX_WIDTH && !target.canScrollHorizontally(1)) {
            scrollTo(MAX_WIDTH, 0);
        }
    }



    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }


    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        // 当RecyclerView在界面之内交给它自己惯性滑动
        return getScrollX() != MAX_WIDTH;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    /**
     * 限制滑动 移动x轴不能超出最大范围
     */
    @Override
    public void scrollTo(int x, int y) {
        if (x < 0) {
            x = 0;
        } else if (x > MAX_WIDTH * 2) {
            x = MAX_WIDTH * 2;
        }
        super.scrollTo(x, y);
    }

    public interface OnDragLeftListener {
        void onDragLeft(boolean isDrag);
    }

    /**
     * 回弹动画
     */
    private class ProgressAnimation extends Animation {

        private ProgressAnimation() {
            isRunAnim = true;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            scrollBy((int) ((MAX_WIDTH - getScrollX()) * interpolatedTime), 0);
            if (interpolatedTime == 1 && isRunAnim) {
                startLeftDrag = false;
                isRunAnim = false;
                if (onDragLeftListener != null) {
                    onDragLeftListener.onDragLeft(startLeftDrag);
                }
            }
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            setDuration(260);
            setInterpolator(new AccelerateInterpolator());
        }
    }
}

