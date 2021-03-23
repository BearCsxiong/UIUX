package me.csxiong.uiux.ui.background

import android.content.Context
import android.graphics.PorterDuff
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemModeBinding

class XferModeViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemModeBinding, PorterDuff.Mode>(context, parent, R.layout.item_mode) {

    override fun onBindViewHolder(position: Int, item: XItem<PorterDuff.Mode>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            mViewBinding.tv.text = it.name
            if (item.isSelect) {
                mViewBinding.tv.setBackgroundResource(R.drawable.used)
            } else {
                mViewBinding.tv.setBackgroundResource(0)
            }
        }
    }
}