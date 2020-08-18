package me.csxiong.uiux.ui.studio.selection

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import me.csxiong.camera.ui.imageloader.ImageLoader
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemSelectionBinding
import me.csxiong.uiux.ui.studio.bean.Selection
import me.csxiong.uiux.utils.ImageUtils

/**
 * @Desc : 选集的ViewHolder
 * @Author : Bear - 2020/8/3
 */
class SelectionViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemSelectionBinding, Selection>(context, parent, R.layout.item_selection) {

    override fun onBindViewHolder(position: Int, item: XItem<Selection>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            ImageLoader.url(ImageUtils.getImagePath(it.image))
                    .into(mViewBinding.iv)

            mViewBinding.tvTitle.text = it.title
            if (item.isSelect) {
                mViewBinding.tvTitle.setTextColor(Color.RED)
            } else {
                mViewBinding.tvTitle.setTextColor(Color.WHITE)
            }
        }
    }
}