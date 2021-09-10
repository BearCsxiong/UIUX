package me.csxiong.uiux.utils.anr

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process
import java.lang.StringBuilder

/**
 * @Desc : 堆栈消息采集器
 * @Author : meitu - 4/13/21
 * [timeSpace] 采集时间间隔
 */
class XStackCollecter(val timeSpace: Long) {

    val thread by lazy { HandlerThread("MainStackTrace") }

    val handler: Handler by lazy {
        object : Handler(thread.looper) {

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                msg?.let {
                    if (it.what == COLLECTION) {
                        //采集一次主线程消息 保存
                        XReflecter.getMainThread()?.let {
                            traceMsg = StringBuilder().apply {
                                append("当前主线程堆栈 -> ").append("\n")
                                for (trace in it.stackTrace) {
                                    append("${trace.className}.${trace.methodName}:${trace.lineNumber}").append("\n")
                                }
                            }.toString()
                        }
                        (it.obj as XStackCollecter).trigger()
                    }
                }
            }
        }
    }

    var traceMsg: String? = null

    companion object {
        const val COLLECTION = 1
    }

    /**
     * 默认初始化 默认定时space抓取主线程trace信息
     */
    init {
        thread.priority = Process.THREAD_PRIORITY_BACKGROUND
        thread.start()
        trigger()
    }

    /**
     * 抓取
     */
    fun trigger() {
        val message = handler.obtainMessage()
        message.obj = this
        message.what = COLLECTION
        handler.sendMessageDelayed(message, timeSpace)
    }
}