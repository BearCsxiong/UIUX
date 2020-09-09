package me.csxiong.uiux.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import me.csxiong.library.utils.XAnimator
import me.csxiong.library.utils.XAnimatorCalculateValuer
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.utils.print

/**
 * @Desc : 一个简易的刷新view
 * @Author : Bear - 2020/8/28
 */
class RefreshLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent3 {

    companion object {
        /**
         * 过滑阻尼
         */
        const val OVERSCROLL_DAMP: Float = 1.8f
        /**
         * 最大过滑距离
         */
        val OVERSCROLL_MAX = XDisplayUtil.dpToPx(250f)
        /**
         * 松手刷新距离
         */
        val REFRESH_DISTANCE = XDisplayUtil.dpToPx(100f)
        /**
         * 加载更多距离
         */
        val LOADMORE_DISTANCE = XDisplayUtil.dpToPx(100f)
    }

    private val topValuer = XAnimatorCalculateValuer()
    private val bottomValuer = XAnimatorCalculateValuer()

    var onRefreshLoadMoreListener: OnRefreshLoadMoreListener? = null

    private val overscrollerAnimator = XAnimator.ofFloat(0f, 1f)
            .duration(300)
            .setAnimationListener(object : XAnimator.XAnimationListener {
                override fun onAnimationEnd(animation: XAnimator?) {
                }

                override fun onAnimationCancel(animation: XAnimator?) {
                }

                override fun onAnimationStart(animation: XAnimator?) {
                }

                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    mRefreshContent?.let {
                        it.layout(it.left, topValuer.caculateValue(fraction).toInt(), it.right, bottomValuer.caculateValue(fraction).toInt())
                    }
                }
            })

    private val damp = OVERSCROLL_DAMP

    private var mRefreshContent: View? = null

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        "onNestedScrollAccepted".print("csx")
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        overscrollerAnimator.cancel()
        mRefreshContent = target
        return true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        "onNestedPreScroll:${type}".print("csx")
        if (type != ViewCompat.TYPE_TOUCH) {
            return
        }
        parent?.requestDisallowInterceptTouchEvent(true)
        var canUp = target.canScrollVertically(Direction.UP)
        var canDown = target.canScrollVertically(Direction.DOWN)
        var scrollUp = dy < 0

        //overscroll情况下 处理滚动
        if ((!canUp && scrollUp) || (!canDown && !scrollUp)) {
            target.offsetTopAndBottom((-dy / damp).toInt())
            consumed[1] = dy
            return
        }

        var fixDy = (dy / damp).toInt()
        if (target.top > 0 && !scrollUp) {
            if (fixDy + target.top < 0) {
                target.offsetTopAndBottom(target.top)
                consumed[1] = -target.top
            } else {
                target.offsetTopAndBottom(-fixDy)
                consumed[1] = dy
            }
        } else if (target.top < 0 && scrollUp) {
            if (fixDy + target.top > 0) {
                target.offsetTopAndBottom(target.top)
                consumed[1] = -target.top
            } else {
                target.offsetTopAndBottom(-fixDy)
                consumed[1] = dy
            }
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        "onNestedScroll".print("csx")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        "onNestedScroll 2".print("csx")
        "${dyConsumed} --- ${dyUnconsumed}".print("csx")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        "onNestedPreScroll 3".print("csx")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        "onStopNestedScroll".print("csx")
        //如果头部距离小于手部释放位置 回归到正常状态
        if (target.top == 0) {
            return
        }
        overscrollerAnimator.cancel()
        if (target.top > 0) {
            if (target.top < REFRESH_DISTANCE) {
                //overScroll -> normal
                topValuer.mark(target.top.toFloat(), 0f)
                bottomValuer.mark(target.bottom.toFloat(), (target.bottom - target.top).toFloat())
                overscrollerAnimator.start()
            } else {
                //回复到刷新位置 并且触发刷新
                //overScroll -> refresh
                topValuer.mark(target.top.toFloat(), REFRESH_DISTANCE)
                bottomValuer.mark(target.bottom.toFloat(), (target.bottom - target.top).toFloat() + REFRESH_DISTANCE)
                overscrollerAnimator.start()
                onRefreshLoadMoreListener?.onRefresh()
            }
        } else {
            if (target.top < -LOADMORE_DISTANCE) {
                //overScroll -> loadMore
                topValuer.mark(target.top.toFloat(), -LOADMORE_DISTANCE)
                bottomValuer.mark(target.bottom.toFloat(), (target.bottom - target.top).toFloat() - LOADMORE_DISTANCE)
                overscrollerAnimator.start()
                onRefreshLoadMoreListener?.onLoadMore()
            } else {
                //overScroll -> normal
                topValuer.mark(target.top.toFloat(), 0f)
                bottomValuer.mark(target.bottom.toFloat(), (target.bottom - target.top).toFloat())
                overscrollerAnimator.start()
            }
        }
    }

    fun finishRefresh() {
        mRefreshContent?.let {
            topValuer.mark(it.top.toFloat(), 0f)
            bottomValuer.mark(it.bottom.toFloat(), (it.bottom - it.top).toFloat())
            overscrollerAnimator.start()
        }
    }

    fun finishLoadMore() {
        mRefreshContent?.let {
            topValuer.mark(it.top.toFloat(), 0f)
            bottomValuer.mark(it.bottom.toFloat(), (it.bottom - it.top).toFloat())
            overscrollerAnimator.start()
        }
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        "dispatchNestedFling".print("csx")
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        "dispatchNestedPreFling".print("csx")
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        "onNestedFling".print("csx")
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        "onNestedPreFling".print("csx")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

}