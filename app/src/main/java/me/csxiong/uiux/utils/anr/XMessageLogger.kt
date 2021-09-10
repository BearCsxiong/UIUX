package me.csxiong.uiux.utils.anr

import android.os.Looper
import android.os.SystemClock
import android.util.Printer
import me.csxiong.uiux.utils.print

/**
 * @Desc : 消息队列打印者
 * @Author : meitu - 4/10/21
 *
 * [maxCpuTimeSpace] 时间间隔
 */
class XMessageLogger(private val maxTimeSpace: Long, private val maxCpuTimeSpace: Long, val xStackCollecter: XStackCollecter) : Printer {

    init {
        Looper.myQueue().addIdleHandler {
            mergeIdleCount++
            true
        }
        Looper.getMainLooper().setMessageLogging(this)
    }

    companion object {
        /**
         * messagequeue开始处理消息开头
         */
        const val MESSAGE_QUEUE_LOG_BEGIN = ">>>>> Dispatching to"

        /**
         * messagequeue处理消息结尾
         */
        const val MESSAGE_QUEUE_LOG_END = "<<<<< Finished to"
    }

    /**
     * 消息开始时间
     */
    var messageCpuStartTime = 0L
    var messageThreadStartTime = 0L

    /**
     * 合并的耗时情况
     */
    var mergeCpuDuration = 0L
    var mergeDuration = 0L
    var mergeIdleCount = 0
    var mergeMsgCount = 0

    /**
     * 基础打印
     */
    override fun println(log: String?) {
        log?.let {
            if (log.startsWith(MESSAGE_QUEUE_LOG_BEGIN)) {
                messageCpuStartTime = SystemClock.elapsedRealtime()
                messageThreadStartTime = SystemClock.currentThreadTimeMillis()
            } else if (log.startsWith(MESSAGE_QUEUE_LOG_END)) {
                val messageCpuProcessTime = SystemClock.elapsedRealtime() - messageCpuStartTime
                val messageThreadProcessTime = SystemClock.currentThreadTimeMillis() - messageThreadStartTime
                //单次消息处理时长超过200ms
                //单次cpu响应时长超过500ms
                if (messageThreadProcessTime > 200 || messageCpuProcessTime > maxCpuTimeSpace) {
                    //前一条和当前耗时均存储
                    MergeMessageQueue.push(MergeMessage(mergeCpuDuration, mergeDuration, mergeMsgCount, mergeIdleCount, null))
                    MergeMessageQueue.pushExceptionMessage(MergeMessage(messageCpuProcessTime, messageThreadProcessTime, 1, 0, null))
                    clear()
                    //test 尝试均打印
                    xStackCollecter.traceMsg?.print("csx")
                    MergeMessageQueue.print().print("csx")
                    return
                }
                //累加
                mergeDuration += messageThreadProcessTime
                mergeCpuDuration += messageCpuProcessTime
                //固定聚合消息打印
                if (mergeDuration > 300) {
                    //如何处理消息总时长超过300ms 合并一次消息
                    MergeMessageQueue.push(MergeMessage(mergeCpuDuration, mergeDuration, mergeMsgCount, mergeIdleCount, null))
                    clear()
                    return
                }
                mergeMsgCount++
            }
        }
    }

    /**
     * 清理合并数据集合
     */
    private fun clear() {
        mergeCpuDuration = 0L
        mergeDuration = 0L
        mergeMsgCount = 0
        mergeIdleCount = 0
    }
}