package me.csxiong.uiux.utils

import android.graphics.BitmapFactory

class BitmapUtils {
    companion object{
        /**
         * 获取bitmap大小
         *
         * @param fileName
         * @return
         */
        fun getBitmapSize(fileName: String): IntArray {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(fileName, options)
            return intArrayOf(options.outWidth, options.outHeight)
        }
    }
}