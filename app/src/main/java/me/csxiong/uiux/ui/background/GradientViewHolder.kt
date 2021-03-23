package me.csxiong.uiux.ui.background

import android.content.Context
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemBackgroundBinding
import me.csxiong.uiux.databinding.ItemGradientBinding

/**
 * @Desc : 背景viewholder
 * @Author : meitu - 3/16/21
 */
class GradientViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemGradientBinding, BackgroundType>(context, parent, R.layout.item_gradient) {

    override fun onBindViewHolder(position: Int, item: XItem<BackgroundType>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            mViewBinding.bv.gradientDrawer.applyBackgroundType(it)
            if (item.isSelect) {
                mViewBinding.fl.setBackgroundResource(R.drawable.used)
            } else {
                mViewBinding.fl.setBackgroundResource(0)
            }
        }
    }

}