package me.csxiong.uiux.ui.studio.bean

import com.google.gson.annotations.SerializedName

/**
 * @Desc : 选集
 * @Author : Bear - 2020/8/3
 */
class Selection {
    var id: String? = null
    var title: String? = null
    var image: String? = null
    var create_time: String? = null
    var update_time: String? = null
    var manhua_id: String? = null
    var sort: String? = null
    var isvip: String? = null
    var score: String? = null
    var view: Any? = null
    var type: String? = null
    var content: Any? = null
    var imagelist: String? = null
    var cjid: String? = null
    var cjname: String? = null
    @SerializedName("switch")
    var switchX: String? = null
    var cjstatus: String? = null
}