package me.csxiong.uiux.ui.studio.page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import me.csxiong.library.utils.XDisplayUtil
import me.csxiong.uiux.utils.ViewUtils

/**
 * @Desc : 大图ImageView
 * @Author : Bear - 2020/8/4
 */
class LargeImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val topImageView by lazy { ImageView(context) }

    val bottomImageView by lazy { ImageView(context) }

    init {
        orientation = LinearLayout.VERTICAL
        addView(topImageView)
        addView(bottomImageView)
    }

    fun setImage(path: String) {
        BitmapFactory.decodeFile(path)?.let {
            var halfHeight = it.height / 2f
            var screenWidth = XDisplayUtil.getScreenWidth()
            var viewHeight = (halfHeight/it.width)*screenWidth
            val topBitmap = Bitmap.createBitmap(it, 0, 0, it.width, halfHeight.toInt())
            val bottomBitmap = Bitmap.createBitmap(it,halfHeight.toInt(),0,it.width,(it.height - halfHeight - 2).toInt())
            ViewUtils.setWidth(topImageView,screenWidth)
            ViewUtils.setWidth(bottomImageView,screenWidth)
            ViewUtils.setHeight(topImageView,viewHeight.toInt())
            ViewUtils.setHeight(bottomImageView,viewHeight.toInt())
            topImageView.setImageBitmap(topBitmap)
            bottomImageView.setImageBitmap(bottomBitmap)
            it.recycle()
        }
    }

}