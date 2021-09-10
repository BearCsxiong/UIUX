package me.csxiong.uiux.utils.anr

import android.os.MessageQueue
import me.csxiong.uiux.utils.print
import java.lang.StringBuilder
import java.util.*

/**
 * @Desc : 聚合消息队列
 * @Author : meitu - 4/13/21
 */
object MergeMessageQueue {

    /**
     * 堆栈最大保存长度
     */
    const val mergeMessageMaxSize = 100

    /**
     * 合并消息List
     */
    private var linkStacks = LinkedList<MergeMessage>()

    /**
     * 压入堆栈
     */
    fun push(mergeMessage: MergeMessage) {
        checkQueue()
        linkStacks.add(mergeMessage)
    }

    /**
     * 压入
     */
    fun pushExceptionMessage(mergeMessage: MergeMessage) {
        checkQueue()
        //反射获取当前处理的message
        XReflecter.getCurrentMessage()?.let {
            mergeMessage.message = it.toString()
        }
        linkStacks.add(mergeMessage)
    }

    /**
     * 检查队列 超长则移除头部
     */
    private fun checkQueue() {
        if (linkStacks.size >= mergeMessageMaxSize) {
            linkStacks.removeAt(0)
        }
    }

    /**
     * 打印目前堆栈
     */
    fun print():String {
        return StringBuilder().apply {
            append("当前聚合消息堆栈 ->").append("\n")
            for (msg in linkStacks) {
                append("cpuDuration:${msg.cpuDuration},duration:${msg.duration},msgCount:${msg.msgCount},idleCount:${msg.idleCount},message:${msg.message}").append("\n")
                append("- - - - - - - - - - - - - - - - -").append("\n")
            }

        }.toString()
    }

}