package me.csxiong.uiux.ui;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import me.csxiong.library.integration.adapter.XItem;
import me.csxiong.library.integration.adapter.XViewHolder;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ItemFeatureBinding;

/**
 * @Desc : 功能点的ViewHolder
 * @Author : csxiong - 2019-11-13
 */
public class FeatureViewHolder extends XViewHolder<ItemFeatureBinding, FeatureBean> {

    public FeatureViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.item_feature);
    }

    @Override
    public void onBindViewHolder(int position, XItem<FeatureBean> item, List<Object> payloads) {
        super.onBindViewHolder(position, item, payloads);
        mViewBinding.tv.setText(item.getEntity().getName());
        addOnChildClickListener(mViewBinding.tv);
    }
}
