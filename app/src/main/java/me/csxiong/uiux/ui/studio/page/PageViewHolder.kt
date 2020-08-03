package me.csxiong.uiux.ui.studio.page

import android.content.Context
import android.graphics.*
import android.view.ViewGroup
import com.bumptech.glide.Glide
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XViewHolder
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ItemScrectBinding
import me.csxiong.uiux.ui.studio.bean.Page
import me.csxiong.uiux.utils.print
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class PageViewHolder(context: Context, parent: ViewGroup) : XViewHolder<ItemScrectBinding, Page>(context, parent, R.layout.item_screct) {

    override fun onBindViewHolder(position: Int, item: XItem<Page>?, payloads: MutableList<Any>?) {
        super.onBindViewHolder(position, item, payloads)
        item?.entity?.let {
            it.path!!.print("csx")
            Glide.with(mContext)
                    .asBitmap()
                    .load(it.path)
                    .into(mViewBinding.iv);

//            object:SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    setBitmapToImg(resource);
//                }
//
//            }
        }
    }

    var mRect = Rect()

    fun setBitmapToImg( resource:Bitmap) {
        try {
            var baos = ByteArrayOutputStream();
            resource.compress(Bitmap.CompressFormat.PNG, 100, baos);

            var isBm =  ByteArrayInputStream(baos.toByteArray());

            //BitmapRegionDecoder newInstance(InputStream is, boolean isShareable)
            //用于创建BitmapRegionDecoder，isBm表示输入流，只有jpeg和png图片才支持这种方式，
            // isShareable如果为true，那BitmapRegionDecoder会对输入流保持一个表面的引用，
            // 如果为false，那么它将会创建一个输入流的复制，并且一直使用它。即使为true，程序也有可能会创建一个输入流的深度复制。
            // 如果图片是逐步解码的，那么为true会降低图片的解码速度。如果路径下的图片不是支持的格式，那就会抛出异常
            val decoder = BitmapRegionDecoder.newInstance(isBm, true)

             val imgWidth = decoder.getWidth();
             val imgHeight = decoder.getHeight();

            var opts =  BitmapFactory.Options();

            //计算图片要被切分成几个整块，
            // 如果sum=0 说明图片的长度不足3000px，不进行切分 直接添加
            // 如果sum>0 先添加整图，再添加多余的部分，否则多余的部分不足3000时底部会有空白
            var sum = imgHeight/3000;

            var redundant = imgHeight%3000;

            var bitmapList =  ArrayList<Bitmap>();

            //说明图片的长度 < 3000
            if (sum == 0){
                //直接加载
                bitmapList.add(resource);
            }else {
                //说明需要切分图片
                for ( i in 0 until sum) {
                    //需要注意：mRect.set(left, top, right, bottom)的第四个参数，
                    //也就是图片的高不能大于这里的4096
                    mRect.set(0, i*3000, imgWidth, (i+1) * 3000);
                    var bm = decoder.decodeRegion(mRect, opts);
                    bitmapList.add(bm);
                }

                //将多余的不足3000的部分作为尾部拼接
                if (redundant > 0){
                    mRect.set(0, sum*3000, imgWidth, imgHeight);
                    var bm = decoder.decodeRegion(mRect, opts);
                    bitmapList.add(bm);
                }

            }

            var bigbitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
            val bigcanvas = Canvas(bigbitmap);

            var paint =  Paint();
            var iHeight = 0

            //将之前的bitmap取出来拼接成一个bitmap
            for (i in 0 until bitmapList.size) {
                var bmp = bitmapList.get(i)
                bigcanvas.drawBitmap(bmp, 0f, iHeight.toFloat(), paint);
                iHeight += bmp.getHeight();

                bmp.recycle();
            }

            mViewBinding.iv.setImageBitmap(bigbitmap);
        } catch ( e: IOException) {
            e.printStackTrace();
        }
    }
}