package me.csxiong.uiux.ui.layoutManager;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author lidiqing
 * @since 2016.3.4
 */
public class FastLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = FastLinearLayoutManager.class.getSimpleName();

    private final float DEFAULT_MILLI_SECOND_PER_INCH = 300f;

    @Snap
    private int snap = Snap.CENTER;

    private float milliSecondPerInch = DEFAULT_MILLI_SECOND_PER_INCH;
    private Context context;
    private int maxItemWidth = 100;

    public FastLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.context = context;
    }

    public void setMaxItemWidth(int maxNodeWidth) {
        this.maxItemWidth = maxNodeWidth;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastSmoothScroller smoothScroller = new FastSmoothScroller(context);
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
        }
    }

    private class FastSmoothScroller extends LinearSmoothScroller {

        public FastSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            // dx = 10000 在 item 项不在可视区域内发生
            // 这时候会以 dx = 10000 的距离进行滑动，知道 item 项可见
            // 如果 item 项离当前距离较近，而时间太短的话，会导致检索 item 项是否可见时，item 项已经滑动
            // 太远，从而无法定位到 targetPosition，直接滑到底。
            // 所以，需要判断 targetPosition 是否距离较近，较近的话时间尽量长，防止滑动太快，没有检索到相应
            // 的项，而导致滑动到底。
            if (dx == 10000) {
                int targetPos = getTargetPosition();
                int firstPos = findFirstVisibleItemPosition();
                int lastPost = findLastVisibleItemPosition();

                int itemWidth = maxItemWidth;
                int offset;
                // 向左滑动
                if (targetPos <= firstPos) {
                    offset = firstPos - targetPos;
                } else {
                    offset = targetPos - lastPost;
                }

                int prepareDx = offset * itemWidth;
                if (prepareDx < 1) {
                    dx = 0;
                } else {
                    dx = 300 * 10000 / prepareDx;
                }
                int time = super.calculateTimeForScrolling(dx);
                return time;
            }

            if (dx > 300) {
                dx = 300;
            }
            int time = super.calculateTimeForScrolling(dx);
            return time;
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            float pp = milliSecondPerInch / displayMetrics.densityDpi;
            return pp;
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            if (snap == Snap.CENTER) {
                return (boxStart + boxEnd) / 2 - (viewEnd + viewStart) / 2;
            } else if (snap == Snap.LEFT) {
                return boxStart - viewStart;
            } else if (snap == Snap.RIGHT) {
                return boxEnd - viewEnd;
            }
            return 0;
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return FastLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }
    }

}
