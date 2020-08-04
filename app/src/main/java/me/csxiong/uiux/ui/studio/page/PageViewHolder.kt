package me.csxiong.uiux.ui.studio.page

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory
import me.csxiong.library.base.APP
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemScrectBinding
import me.csxiong.uiux.ui.studio.bean.Page
import me.csxiong.uiux.utils.print
import java.io.File

class PageViewHolder(context: Context, parent: ViewGroup) : XViewHolder<ItemScrectBinding, Page>(context, parent, R.layout.item_screct) {

    override fun onBindViewHolder(position: Int, item: XItem<Page>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            it.path!!.print("csx")
            Glide.with(APP.get())
                    .downloadOnly()
                    .load(it.path)
                    .addListener(object : RequestListener<File> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                            return true
                        }

                        override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            resource?.let {
                                mViewBinding.iv.setImage(FileBitmapDecoderFactory(it.path))
                            }
                            return true
                        }
                    })
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        }
    }

}