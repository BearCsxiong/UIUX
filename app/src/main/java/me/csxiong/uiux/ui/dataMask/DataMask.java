package me.csxiong.uiux.ui.dataMask;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.csxiong.uiux.ui.dataMask.mask.BaseMask;

/**
 * @Desc : 数据蒙版
 * @Author : csxiong - 2020-02-13
 */
public class DataMask extends RelativeLayout {

    /**
     * 蒙版
     */
    private HashMap<String, BaseMask> masks = new HashMap<String, BaseMask>();

    /**
     * 当前Mask类型
     */
    private String currentMaskType;

    /**
     * 数据蒙版帮助类
     */
    private DataMaskHelper dataMaskHelper;

    public DataMask(@NonNull Context context) {
        super(context);
    }

    public DataMask(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DataMask(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    /**
     * 获取数据蒙版帮助类
     * 主要添加部分蒙版内部的
     * 1.监听注册
     * 2.事件注册
     *
     * @return
     */
    public DataMaskHelper getDataMaskHelper() {
        if (dataMaskHelper == null) {
            dataMaskHelper = new DataMaskHelper();
        }
        return dataMaskHelper;
    }

    /**
     * 初始化
     */
    private void init() {
        //添加所有默认的Masks
        if (masksDelegate != null) {
            masksDelegate.onCreateMasks(masks);
        }
    }

    /**
     * 获取Mask
     *
     * @param tag 目标tag的Mask
     * @return
     */
    public BaseMask getMask(String tag) {
        return masks.get(tag);
    }

    /**
     * 仅显示目标tag Mask
     *
     * @param tag
     */
    public void showMaskOnly(String tag) {
        for (String _tag : masks.keySet()) {
            //默认立即隐藏
            hideMask(_tag);
        }
        showMask(tag);
    }

    /**
     * 显示目标Mask
     *
     * @param tag
     */
    public void showMask(String tag) {
        currentMaskType = tag;
        BaseMask mask = masks.get(tag);
        if (mask != null) {
            int index = indexOfChild(mask.getViewStub());
            if (index < 0) {
                mask.onCreateViewStub(this, dataMaskHelper);
            }
            mask.getViewStub().setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏目标Mask
     *
     * @param tag
     */
    public void hideMask(String tag) {
        currentMaskType = null;
        BaseMask mask = masks.get(tag);
        if (mask != null && mask.getViewStub() != null) {
            int index = indexOfChild(mask.getView());
            if (index >= 0) {
                mask.getViewStub().setVisibility(GONE);
            }
        }
    }

    /**
     * 隐藏所有Mask
     */
    public void hideAll() {
        for (String _tag : masks.keySet()) {
            //默认立即隐藏
            hideMask(_tag);
        }
    }

    public String getCurrentMaskType() {
        return currentMaskType;
    }

    private static MasksDelegate masksDelegate;

    public interface MasksDelegate {

        void onCreateMasks(Map<String, BaseMask> masks);
    }

    public static void setMasksDelegate(MasksDelegate masksDelegate) {
        DataMask.masksDelegate = masksDelegate;
    }
}
