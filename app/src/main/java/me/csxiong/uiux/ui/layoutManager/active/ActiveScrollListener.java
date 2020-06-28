package me.csxiong.uiux.ui.layoutManager.active;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Desc : Active粘性滑动监听
 * @Author : Bear - 2020/6/19
 */
public abstract class ActiveScrollListener extends RecyclerView.OnScrollListener implements AbsActiveScrollListener {

    /**
     * 宿主
     */
    private RecyclerView mRv;

    /**
     * 线性布局LayoutManager
     */
    private LinearLayoutManager mLayoutManager;

    /**
     * 当前粘性位置
     */
    private int activePosition = -1;

    private long activeTime = System.currentTimeMillis();

    /**
     * 上一次滑动状态
     */
    private int lastState;

    /**
     * 选中策略
     */
    private ActiveStrategy strategy;

    /**
     * 是否懒粘性
     */
    private boolean isLazyActive;

    /**
     * 滑动监听
     *
     * @param mRv          目标对象
     * @param strategy     策略
     * @param isLazyActive 是否懒粘性
     */
    public ActiveScrollListener(RecyclerView mRv, ActiveStrategy strategy, boolean isLazyActive) {
        this.mRv = mRv;
        this.mLayoutManager = (LinearLayoutManager) mRv.getLayoutManager();
        this.strategy = strategy;
        this.isLazyActive = isLazyActive;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isLazyActive) {
            onScrollFirstPosition(mLayoutManager.findFirstVisibleItemPosition());
            onScrollLastPosition(mLayoutManager.findLastVisibleItemPosition());
            resetActive();
//            checkDeActive();
        } else {
            checkActive();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (isLazyActive) {
            if (lastState == RecyclerView.SCROLL_STATE_DRAGGING || lastState == RecyclerView.SCROLL_STATE_SETTLING) {
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    checkDeActive();
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    checkActive();
                }
            }
            lastState = newState;
        }
    }

    private Rect childRect = new Rect();

    private Rect parentRect = new Rect();

    /**
     * 获取Active百分比
     *
     * @param childView 子View
     * @return
     */
    private float getActivePercent(View childView) {
        if (childView == null) {
            return 0;
        }
        childView.getGlobalVisibleRect(childRect);
        if (childRect.bottom < 0) {
            return 0;
        }
        mRv.getGlobalVisibleRect(parentRect);
        if (childRect.top >= parentRect.bottom) {
            return 0;
        }
        if (childView.getHeight() == 0) {
            return 999;
        }
        if (parentRect.height() == 0) {
            return 999;
        }
        if (childRect.centerY() < 0) {
            return 0;
        }
        return strategy.onCalculatePercent(childRect, parentRect);
    }

    /**
     * 找到Active粘性位置
     *
     * @param startPosition
     * @param endPosition
     * @param range
     */
    private void findActivePosition(int startPosition, int endPosition, int range) {
        int activePosition = startPosition;
        float maxPercent = 0;
        for (int i = 0; i <= range; i++) {
            float percent = getActivePercent(getTargetView(startPosition + i));
            if (percent == 999) {
                onActiveFind(startPosition, 0, 0);
                return;
            }
            if (percent > maxPercent) {
                maxPercent = percent;
                activePosition = startPosition + i;
                if (percent == 1) {
                    break;
                }
            }
        }
        onActiveFind(activePosition, startPosition, endPosition);
    }

    /**
     * 找到Active
     *
     * @param newActivePosition 新的粘性位置
     * @param startPosition
     * @param endPosition
     */
    protected void onActiveFind(int newActivePosition, int startPosition, int endPosition) {
        if (activePosition != newActivePosition) {
            //快速滑动，deActive事件已经被消费
            if (activePosition != -1) {
                onDeActive(activePosition);
                onActiveTime(activePosition, System.currentTimeMillis() - activeTime);
            }
            activePosition = newActivePosition;
            onActive(activePosition);
            activeTime = System.currentTimeMillis();
        }
    }

    /**
     * 检测Active粘性事件
     */
    private void checkActive() {
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
        int range = lastVisibleItemPosition - firstVisibleItemPosition;
        if (range == 0) {
            if (activePosition != firstVisibleItemPosition) {
                activePosition = firstVisibleItemPosition;
                if (activePosition != -1) {
                    onActive(activePosition);
                }
            }
        } else {
            findActivePosition(firstVisibleItemPosition, lastVisibleItemPosition, range);
        }
    }

    /**
     * 检测DeActive事件
     */
    private void checkDeActive() {
        if (activePosition != -1) {
            //1.完全划出界面 onDeActive
            float activePercent = getActivePercent(getTargetView(activePosition));
            if (activePercent < 0.2f || activePercent == 0.0) {
                onDeActive(activePosition);
                activePosition = -1;
            }
        }
    }

    /**
     * 重新检测Active
     */
    public void recheckActive() {
        if (activePosition != -1) {
            onDeActive(activePosition);
            activePosition = -1;
        }
        checkActive();
    }

    /**
     * 重置选中态
     */
    public void resetActive() {
        if (activePosition != -1) {
            onDeActive(activePosition);
            activePosition = -1;
        }
    }

    public void onScrollLastPosition(int lastPosition) {
    }

    public void onScrollFirstPosition(int firstPosition) {
    }

    public int getActivePosition() {
        return activePosition;
    }

}
