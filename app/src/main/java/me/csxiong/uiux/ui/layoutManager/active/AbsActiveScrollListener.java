package me.csxiong.uiux.ui.layoutManager.active;

import android.view.View;

/**
 * Created by csxiong on 2019/1/29.
 */

public interface AbsActiveScrollListener {

    /**
     * 获取测量对象处于
     *
     * @return
     */
    View getTargetView(int position);

    /**
     * 最关注行
     *
     * @param position
     */
    void onActive(int position);

    /**
     * 移除关注对象行
     *
     * @param position
     */
    void onDeActive(int position);

    default void onActiveTime(int activePosition, long timeMiles) {
    }

}
