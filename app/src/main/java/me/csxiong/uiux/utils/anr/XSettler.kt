package me.csxiong.uiux.utils.anr

/**
 * @Desc : 性能工具
 * @Author : meitu - 4/10/21
 */
class XSettler {

    companion object {

        /**
         * 默认最大处理耗时采集
         */
        const val DEFAULT_MAX_TIME_SPACE = 200L

        /**
         * 默认最大CPU间隔耗时采集
         */
        const val DEFAULT_MAX_CPU_TIME_SPACE = 500L

        /**
         * 默认UI dump间隔
         */
        const val DEFAULT_UI_DUMP_TIME_SPACE = 200L

        fun init() {
            XMessageLogger(DEFAULT_MAX_TIME_SPACE, DEFAULT_MAX_CPU_TIME_SPACE, XStackCollecter(DEFAULT_UI_DUMP_TIME_SPACE))
        }
    }
}