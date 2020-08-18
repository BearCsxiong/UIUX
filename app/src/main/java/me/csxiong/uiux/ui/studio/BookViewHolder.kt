package me.csxiong.uiux.ui.studio

import android.content.Context
import android.view.ViewGroup
import me.csxiong.camera.ui.imageloader.ImageLoader
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemBookListBinding
import me.csxiong.uiux.ui.studio.bean.Book
import me.csxiong.uiux.utils.ImageUtils

/**
 * @Desc : 图书基本book
 * @Author : Bear - 2020/8/3
 */
class BookViewHolder(context: Context, viewGroup: ViewGroup) : XViewHolder<ItemBookListBinding, Book>(context, viewGroup, R.layout.item_book_list) {

    override fun onBindViewHolder(position: Int, item: XItem<Book>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            ImageLoader.url(ImageUtils.getImagePath(it.cover))
                    .into(mViewBinding.iv)

            it.title?.let {
                mViewBinding.tvTitle.text = it
            }

            it.last_chapter_title?.let {
                mViewBinding.tvLastCapter.text = it
            }

        }
    }
}