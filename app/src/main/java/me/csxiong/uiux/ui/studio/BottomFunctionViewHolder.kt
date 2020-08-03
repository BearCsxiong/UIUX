package me.csxiong.uiux.ui.studio

import android.content.Context
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemBottomFunctionBinding

/**
 * @Desc : 底部功能栏
 * @Author : Bear - 2020/8/3
 */
class BottomFunctionViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemBottomFunctionBinding, BottomFunction>(context, parent, R.layout.item_bottom_function) {

    override fun onBindViewHolder(position: Int, item: XItem<BottomFunction>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            mViewBinding.tv.text = it.functionName
        }
    }
}