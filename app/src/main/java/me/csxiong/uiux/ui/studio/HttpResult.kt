package me.csxiong.uiux.ui.studio

import com.google.gson.annotations.SerializedName

/**
 * @Desc : 因为目前网络请求结构后台封装很差 没有统一外包装结构 只能前端去兼容结构
 * @Author : Bear - 2020-03-30
 */
class HttpResult<T> {
    /**
     * 状态码
     */
    @SerializedName("code")
    var code = 0
    /**
     * 请求描述信息
     */
    @SerializedName("msg")
    var msg: String? = null
    /**
     * 主数据
     */
    @SerializedName("result")
    var data: T? = null
        private set
    /**
     * 额外的数据结构 兼容一些后端"垃圾结构"
     */
    @SerializedName("succ")
    var isSuccess = false

    fun setData(data: T) {
        this.data = data
    }

}