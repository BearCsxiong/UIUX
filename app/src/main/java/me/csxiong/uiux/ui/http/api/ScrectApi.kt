package me.csxiong.uiux.ui.http.api

import me.csxiong.uiux.ui.http.HttpResult
import me.csxiong.uiux.ui.http.ResponseListener
import me.csxiong.uiux.ui.http.XHttp
import me.csxiong.uiux.ui.book.bean.BookList
import me.csxiong.uiux.ui.book.bean.Episode

class ScrectApi {

    companion object {
        const val Host = "https://m.yymhz.com"
    }

    /**
     * 书本 以及对应的下一集的数据
     */
    fun chapterNearBy(capterId: Int, responseListener: ResponseListener<HttpResult<Episode>>?) {
        XHttp.getInstance()["/home/api/chapter_nearby"]
                .addParameters("manhua_id", 14389)
                .addParameters("capter_id", capterId)
                .addParameters("next", 1)
                .execute(responseListener)
    }

    fun chapterList(capterId: Int, page: Int, pageNumber: Int, responseListener: ResponseListener<Any>) {
        XHttp.getInstance()["/home/api/chapter_list/tp/list/${capterId}-1-${page}-${pageNumber}"]
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