package com.commsource.util.scroll

import android.view.View

/**
 * Created by csxiong on 2019/1/29.
 */
interface AbsActiveScrollListener {
    /**
     * 获取测量对象
     *
     * @return
     */
    fun getTargetView(position: Int): View?

    /**
     * 最关注行
     *
     * @param position
     */
    fun onActive(position: Int)

    /**
     * 移除关注对象行
     *
     * @param position
     */
    fun onDeActive(position: Int)

    fun onActiveTime(activePosition: Int, timeMiles: Long) {}
}