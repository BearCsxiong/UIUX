package me.csxiong.uiux.ui.seek;

import android.graphics.Canvas;

/**
 * @Desc : SeekBar绘制组成部分
 * @Author : Bear - 2020/7/27
 */
public abstract class XSeekDrawPart {

    /**
     * 依靠的父类
     */
    XSeekBar parent;

    public XSeekDrawPart(XSeekBar xSeekBar) {
        this.parent = xSeekBar;
    }

    /**
     * 绘制部分
     *
     * @param canvas
     */
    public abstract void onDraw(Canvas canvas);

    /**
     * 初始化尺寸
     *
     * @param width  控件总宽度
     * @param height 控件总高度
     */
    public abstract void initSize(int width, int height);

}
