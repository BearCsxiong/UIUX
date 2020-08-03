package me.csxiong.uiux.ui.book

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import me.csxiong.library.base.APP
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemScrectBinding
import me.csxiong.uiux.ui.book.bean.Page
import me.csxiong.uiux.utils.print

class ScrectViewHolder(context: Context?, parent: ViewGroup?) : XViewHolder<ItemScrectBinding?, Page?>(context, parent, R.layout.item_screct) {

    override fun onBindViewHolder(position: Int, item: XItem<Page?>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            it.path!!.print("csx")
            Glide.with(APP.get())
                    .load(it.path)
                    .into(mViewBinding!!.iv)
        }
    }
}