package me.csxiong.uiux.ui.color

import android.content.Context
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.library.utils.XAnimatorCaculateValuer
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemColorBinding
import me.csxiong.uiux.utils.ViewUtils

/**
 * @Desc : 色轮ViewHolder
 * @Author : Bear - 2020/6/19
 */
class ColorWheelViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemColorBinding, Int>(context, parent, R.layout.item_color) {

    val scaleValuer = XAnimatorCaculateValuer().apply { mark(1f, 0.8f) }

    override fun onBindViewHolder(position: Int, item: XItem<Int>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        if (payloads != null && payloads.isNotEmpty()) {
            val fraction = payloads.get(0);
            val value = scaleValuer.caculateValue(fraction as Float)
            ViewUtils.setWidth(itemView, (value * XDisplayUtil.dpToPx(100f)).toInt())
        }

    }
}