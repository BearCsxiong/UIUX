package me.csxiong.uiux.ui.vernier

interface OnProgressChangeListener {

    /**
     * 开始拖动
     *
     * @param progress
     */
    fun onStartTracking(progress: Int, scrollX: Int)

    /**
     * 进度改变
     *
     * @param progress
     * @param fromUser
     */
    fun onProgressChange(progress: Int, scrollX: Int, fromUser: Boolean)

    /**
     * 停止拖动
     *
     * @param progress
     */
    fun onStopTracking(progress: Int, scrollX: Int, fromUser: Boolean)
}