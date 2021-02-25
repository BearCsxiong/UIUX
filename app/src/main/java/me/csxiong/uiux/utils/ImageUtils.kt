package me.csxiong.uiux.utils

class ImageUtils {
    companion object {
//        const val IMAGE_HOST = "http://101.71.85.89/a5.jiayouzhibo.com/"
        const val IMAGE_HOST = "http://web1.jiayouzhibo.com"
//        const val IMAGE_HOST = "http://101.71.85.97/a1.jiayouzhibo.com/"

        fun getImagePath(path: String?): String {
            return IMAGE_HOST + path
        }
    }
}