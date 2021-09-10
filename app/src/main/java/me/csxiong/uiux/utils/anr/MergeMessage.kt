package me.csxiong.uiux.utils.anr

/**
 * @Desc : 合并消息
 * @Author : meitu - 4/13/21
 * [cpuDuration] cpu处理时长
 * [duration] 真是线程处理时长
 * [msgCount] 处理消息数量
 * [idleCount] 进入idle次数
 * [message] 重要消息 非必要 单次耗时严重消息采集
 */
data class MergeMessage(val cpuDuration: Long, val duration: Long, val msgCount: Int, val idleCount: Int, var message: String? = null)