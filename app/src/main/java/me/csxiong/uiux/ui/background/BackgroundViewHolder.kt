package me.csxiong.uiux.ui.background

import android.content.Context
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemBackgroundBinding

/**
 * @Desc : 背景ViewHodler
 * @Author : meitu - 3/18/21
 */
class BackgroundViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemBackgroundBinding, Int>(context, parent, R.layout.item_background) {

    override fun onBindViewHolder(position: Int, item: XItem<Int>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            mViewBinding.iv.setImageResource(it)
            if (item.isSelect) {
                mViewBinding.fl.setBackgroundResource(R.drawable.used)
            } else {
                mViewBinding.fl.setBackgroundResource(0)
            }
        }
    }
}