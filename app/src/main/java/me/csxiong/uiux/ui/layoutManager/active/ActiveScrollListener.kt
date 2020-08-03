package com.commsource.util.scroll

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import me.csxiong.uiux.ui.layoutManager.active.ActiveStrategy

/**
 * @Desc : Active粘性滑动监听
 * @Author : Bear - 2020/6/19
 */
abstract class ActiveScrollListener(private val mRv: RecyclerView, strategy: ActiveStrategy, isLazyActive: Boolean) : RecyclerView.OnScrollListener(), AbsActiveScrollListener {
    /**
     * 线性布局LayoutManager
     */
    private val mLayoutManager: LinearLayoutManager?
    /**
     * 当前粘性位置
     */
    var activePosition = -1
        private set
    private var activeTime = System.currentTimeMillis()
    /**
     * 上一次滑动状态
     */
    private var lastState = 0
    /**
     * 选中策略
     */
    private val strategy: ActiveStrategy
    /**
     * 是否懒粘性
     */
    private val isLazyActive: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isLazyActive) {
            if (lastState == RecyclerView.SCROLL_STATE_DRAGGING) {
                onScrollFirstPosition(mLayoutManager!!.findFirstVisibleItemPosition())
                onScrollLastPosition(mLayoutManager.findLastVisibleItemPosition())
                resetActive()
            }
            //            checkDeActive();
        } else {
            checkActive()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (lastState == RecyclerView.SCROLL_STATE_DRAGGING || lastState == RecyclerView.SCROLL_STATE_SETTLING) {
            if (isLazyActive) {
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    checkDeActive()
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    checkActive()
                    onScrollStop(activePosition)
                }
            } else {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onScrollStop(activePosition)
                }
            }
        }
        lastState = newState

    }

    private val childRect = Rect()
    private val parentRect = Rect()
    /**
     * 获取Active百分比
     *
     * @param childView 子View
     * @return
     */
    private fun getActivePercent(childView: View?): Float {
        if (childView == null) {
            return 0f
        }
        childView.getGlobalVisibleRect(childRect)
        if (childRect.bottom < 0) {
            return 0f
        }
        mRv.getGlobalVisibleRect(parentRect)
        if (childRect.top >= parentRect.bottom) {
            return 0f
        }
        if (childView.height == 0) {
            return 999f
        }
        if (parentRect.height() == 0) {
            return 999f
        }
        return if (childRect.centerY() < 0) {
            0f
        } else strategy.onCalculatePercent(childRect, parentRect)
    }

    /**
     * 找到Active粘性位置
     *
     * @param startPosition
     * @param endPosition
     * @param range
     */
    private fun findActivePosition(startPosition: Int, endPosition: Int, range: Int) {
        var activePosition = startPosition
        var maxPercent = 0f
        for (i in 0..range) {
            var curPosition = startPosition + i
            val percent = getActivePercent(getTargetView(curPosition))
            onPositionPercentChange(curPosition, percent)
            if (percent > maxPercent) {
                maxPercent = percent
                activePosition = startPosition + i
            }
        }
        onActiveFind(activePosition, startPosition, endPosition)
    }

    /**
     * 找到Active
     *
     * @param newActivePosition 新的粘性位置
     * @param startPosition
     * @param endPosition
     */
    protected fun onActiveFind(newActivePosition: Int, startPosition: Int, endPosition: Int) {
        if (activePosition != newActivePosition) { //快速滑动，deActive事件已经被消费
            if (activePosition != -1) {
                onDeActive(activePosition)
                onActiveTime(activePosition, System.currentTimeMillis() - activeTime)
            }
            activePosition = newActivePosition
            onActive(activePosition)
            activeTime = System.currentTimeMillis()
        }
    }

    /**
     * 检测Active粘性事件
     */
    private fun checkActive() {
        val firstVisibleItemPosition = mLayoutManager!!.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        val range = lastVisibleItemPosition - firstVisibleItemPosition
        if (range == 0) {
            if (activePosition != firstVisibleItemPosition) {
                activePosition = firstVisibleItemPosition
                if (activePosition != -1) {
                    onActive(activePosition)
                }
            }
        } else {
            findActivePosition(firstVisibleItemPosition, lastVisibleItemPosition, range)
        }
    }

    /**
     * 检测DeActive事件
     */
    private fun checkDeActive() {
        if (activePosition != -1) { //1.完全划出界面 onDeActive
            val activePercent = getActivePercent(getTargetView(activePosition))
            if (activePercent < 0.2f || activePercent.toDouble() == 0.0) {
                onDeActive(activePosition)
                activePosition = -1
            }
        }
    }

    /**
     * 重新检测Active
     */
    fun recheckActive() {
        if (activePosition != -1) {
            onDeActive(activePosition)
            activePosition = -1
        }
        checkActive()
    }

    /**
     * 重置选中态
     */
    fun resetActive() {
        if (activePosition != -1) {
            onDeActive(activePosition)
            activePosition = -1
        }
    }

    fun onScrollLastPosition(lastPosition: Int) {}
    fun onScrollFirstPosition(firstPosition: Int) {}
    open fun onScrollStop(activePosition: Int) {}
    open fun onPositionPercentChange(position: Int, crossPercent: Float) {

    }

    /**
     * 滑动监听
     *
     * @param mRv          目标对象
     * @param strategy     策略
     * @param isLazyActive 是否懒粘性
     */
    init {
        mLayoutManager = mRv.layoutManager as LinearLayoutManager?
        this.strategy = strategy
        this.isLazyActive = isLazyActive
    }
}