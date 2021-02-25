package me.csxiong.uiux.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import me.csxiong.library.utils.XAnimator
import me.csxiong.library.utils.XAnimatorCalculateValuer
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.utils.print

class ActionLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent3 {

    var scrollChild: View? = null

    var maxTransitionY = XDisplayUtil.dpToPx(150f)

    var energyTransitionY = XDisplayUtil.dpToPx(15f)

    private val transitionYValuer = XAnimatorCalculateValuer()

    var hasFlingInGesture = false

    val overScroller by lazy { OverScroller(context, AccelerateDecelerateInterpolator()) }

    private val autoLocationAnimator = XAnimator.ofFloat(0f, 1f)
            .duration(100)
            .interpolator(LinearInterpolator())
            .setAnimationListener(object : XAnimator.XAnimationListener {
                override fun onAnimationEnd(animation: XAnimator?) {
                }

                override fun onAnimationCancel(animation: XAnimator?) {
                }

                override fun onAnimationStart(animation: XAnimator?) {
                }

                override fun onAnimationUpdate(fraction: Float, value: Float) {
                    scrollChild?.translationY = transitionYValuer.caculateValue(fraction)
                }
            })

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        scrollChild = target
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        "onStartNestedScroll".print("csx")
        return true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        "onNestedPreScroll".print("csx")
        if (type == ViewCompat.TYPE_TOUCH) {
            if (scrollChild is RecyclerView) {
                scrollChild?.let {
                    //仅拦截向上滑动
                    var scrollUp = dy > 0
                    if (scrollUp) {
                        //向上
                        if (it.translationY >= 0) {
                            var resultY = it.translationY - dy
                            if (resultY < 0) {
                                consumed[1] = it.translationY.toInt()
                                it.translationY = 0f
                            } else {
                                it.translationY -= dy
                                consumed[1] = dy
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        "onNestedScroll".print("csx")
        if (type == ViewCompat.TYPE_TOUCH) {
            if (scrollChild is RecyclerView) {
                scrollChild?.let {
                    //处理向下滑动
                    var scrollUp = dyUnconsumed > 0
                    if (!scrollUp) {
                        //向下
                        if (it.translationY <= maxTransitionY) {
                            var resultY = it.translationY - dyUnconsumed
                            if (resultY > maxTransitionY) {
                                consumed[1] = (it.translationY - maxTransitionY).toInt()
                                it.translationY = maxTransitionY
                            } else {
                                it.translationY -= dyUnconsumed
                                consumed[1] = dyUnconsumed
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        "onNestedPreFling".print("csx")
        hasFlingInGesture = true
        var handleFling = false
        scrollChild?.let {
            var scrollUp = velocityY > 0
            overScroller.fling(0, it.translationY.toInt(), 0, -velocityY.toInt(), 0, 0, 0, maxTransitionY.toInt())
            //势能处理
            if (scrollUp) {
                handleFling = true
                autoLocationAnimator.cancel()
                if (overScroller.finalY <= maxTransitionY - energyTransitionY) {
                    transitionYValuer.mark(it.translationY, 0f)
                } else {
                    transitionYValuer.mark(it.translationY, maxTransitionY)
                }
                autoLocationAnimator.start()
            } else if (!it.canScrollVertically(Direction.UP)) {
                handleFling = true
                autoLocationAnimator.cancel()
                if (overScroller.finalY >= energyTransitionY) {
                    transitionYValuer.mark(it.translationY, maxTransitionY)
                } else {
                    transitionYValuer.mark(it.translationY, 0f)
                }
                autoLocationAnimator.start()
            }
        }
        if (handleFling) {
            return true
        }
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        "onStopNestedScroll:${type}".print("csx")
        if (type == ViewCompat.TYPE_TOUCH) {
            //手部离开
            if (!hasFlingInGesture) {
                scrollChild?.let {
                    autoLocationAnimator.cancel()
                    //如果没有fling事件触发 需要直接触发滚动边界判断
                    if (it.translationY <= maxTransitionY / 2) {
                        transitionYValuer.mark(it.translationY, 0f)
                    } else {
                        transitionYValuer.mark(it.translationY, maxTransitionY)
                    }
                    autoLocationAnimator.start()
                }
            }
        }
        //清理掉Fling标记位
        hasFlingInGesture = false
    }

}