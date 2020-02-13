package me.csxiong.uiux.ui.dataMask;

import android.view.View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.csxiong.uiux.ui.dataMask.listener.OnInflateListener;

/**
 * @Desc : 数据蒙版帮助类
 * @Author : csxiong - 2020-02-13
 */
public class DataMaskHelper {

    private Map<String, MaskActions> maskMap;

    private View.OnClickListener onClickListener;

    public DataMaskHelper() {
    }

    private Builder builder;

    public Builder newBuilder() {
        if (builder == null) {
            builder = new Builder(this);
        }
        if (maskMap != null) {
            maskMap.clear();
        }
        if (onClickListener != null) {
            onClickListener = null;
        }
        return builder;
    }

    public static class Builder {

        private DataMaskHelper dataMaskHelper;

        public Builder(DataMaskHelper helper) {
            dataMaskHelper = helper;
            dataMaskHelper.maskMap = new HashMap<>();
        }

        public Builder bindView(String tag, int resId) {
            MaskActions maskActions = dataMaskHelper.maskMap.get(tag);
            if (maskActions == null) {
                MaskActions _maskActions = new MaskActions(new ArrayList<>());
                _maskActions.getMaskActions().add(new MaskAction(resId, tag));
                dataMaskHelper.maskMap.put(tag, _maskActions);
            } else {
                maskActions.getMaskActions().add(new MaskAction(resId, tag));
            }
            return this;
        }

        public Builder setOnInflateViewListener(String tag, OnInflateListener listener) {
            MaskActions maskActions = dataMaskHelper.maskMap.get(tag);
            if (maskActions == null) {
                MaskActions _maskActions = new MaskActions(new ArrayList<>());
                _maskActions.setOnInflateListener(listener);
                dataMaskHelper.maskMap.put(tag, _maskActions);
            } else {
                maskActions.setOnInflateListener(listener);
            }
            return this;
        }

        public Builder bindClickListener(View.OnClickListener listener) {
            dataMaskHelper.onClickListener = listener;
            return this;
        }

        public DataMaskHelper build() {
            return dataMaskHelper;
        }
    }

    public Map<String, MaskActions> getMaskMap() {
        return maskMap;
    }

    public void setMaskMap(Map<String, MaskActions> maskMap) {
        this.maskMap = maskMap;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
