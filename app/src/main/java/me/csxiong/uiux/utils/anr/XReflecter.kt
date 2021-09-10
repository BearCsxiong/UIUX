package me.csxiong.uiux.utils.anr

import android.annotation.SuppressLint
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import me.csxiong.uiux.utils.print
import java.lang.Exception

/**
 * @Desc : 反射处理
 * @Author : meitu - 4/13/21
 */
class XReflecter {

    companion object {
        /**
         * 获取Message
         */
        fun getCurrentMessage(): Message? {
            try {
                val clazz = Class.forName("android.os.MessageQueue")
                val field = clazz.getDeclaredField("mMessages")
                field.isAccessible = true
                return field.get(getMessageQueue()) as Message
            } catch (e: Exception) {
                "getCurrentMessage:${e.message}".print("csx")
                return null
            }
        }

        @SuppressLint("NewApi")
        private fun getMessageQueue(): MessageQueue {
            return Looper.getMainLooper().queue
        }

        fun getMainThread(): Thread? {
            return Looper.getMainLooper().thread
        }
    }

}