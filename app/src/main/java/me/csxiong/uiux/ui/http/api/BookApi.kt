package me.csxiong.uiux.ui.http.api

import me.csxiong.uiux.ui.http.HttpResult
import me.csxiong.uiux.ui.http.ResponseListener
import me.csxiong.uiux.ui.http.XHttp
import me.csxiong.uiux.ui.studio.bean.BookList
import me.csxiong.uiux.ui.studio.bean.Selection
import me.csxiong.uiux.ui.studio.bean.SelectionList

class BookApi {

    companion object {
        const val Host = "https://m.yymhz.com"
    }

    /**
     * 书本 以及对应的下一集的数据
     */
    fun chapterNearBy(capterId: Int, responseListener: ResponseListener<HttpResult<Selection>>?) {
        XHttp.getInstance()["/home/api/chapter_nearby"]
                .addParameters("manhua_id", 14389)
                .addParameters("capter_id", capterId)
                .addParameters("next", 0)
                .execute(responseListener)
    }

    /**
     * 书本选集列表
     */
    fun chapterList(capterId: Int, page: Int, pageNumber: Int, responseListener: ResponseListener<HttpResult<SelectionList>>) {
        XHttp.getInstance()["/home/api/chapter_list/tp/${capterId}-1-${page}-${pageNumber}"]
                .execute(responseListener)
    }

    /**
     * 最新书本List
     */
    fun newBookList(page: Int, responseListener: ResponseListener<HttpResult<BookList>>) {
        XHttp.getInstance()["/home/api/getpage/tp/1-newest-${page}"]
                .execute(responseListener)
    }


}