package me.csxiong.uiux.ui.color

import android.content.Context
import android.view.ViewGroup
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.library.utils.XAnimatorCalculateValuer
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemColorBinding

/**
 * @Desc : 色轮ViewHolder
 * @Author : Bear - 2020/6/19
 */
class ColorWheelViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemColorBinding, Int>(context, parent, R.layout.item_color) {

    val scaleValuer = XAnimatorCalculateValuer()
            .apply {
                mark(1.0f, 1.2f)
            }

    override fun onBindViewHolder(position: Int, item: XItem<Int>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        mViewBinding.tv.setText(position.toString())
    }

    fun updateFraction(fraction: Float) {
        mViewBinding.iv.scaleX = scaleValuer.caculateValue(fraction)
        mViewBinding.iv.scaleY = scaleValuer.caculateValue(fraction)
    }
}