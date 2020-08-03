package me.csxiong.uiux.ui.book

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import me.csxiong.library.base.XViewModel
import me.csxiong.library.utils.XToast
import me.csxiong.uiux.ui.book.bean.Book
import me.csxiong.uiux.ui.book.bean.BookList
import me.csxiong.uiux.ui.http.HttpResult
import me.csxiong.uiux.ui.http.ResponseListener
import me.csxiong.uiux.ui.http.XHttp
import me.csxiong.uiux.ui.http.api.ScrectApi
import javax.inject.Inject

class BookViewModel @Inject constructor(application: Application) : XViewModel(application) {

    var hostPah = "http://101.71.85.97/a1.jiayouzhibo.com/"

    var dataList = ArrayList<Book>()

    var dataEvent = MutableLiveData<List<Book>>()

    var page = 0

    var hasNext = true

//    fun request(capterId: Int) {
//        XHttp.getService(ScrectApi::class.java)
//                .chapterNearBy(capterId, object : ResponseListener<HttpResult<Episode>> {
//
//                    override fun onError(throwable: Throwable) {
//                    }
//
//                    override fun onNext(t: HttpResult<Episode>?) {
//                        t?.data?.let {
//                            episodes.add(it)
//                            val pages = ArrayList<Page>()
//                            val split = it.imagelist!!.split(",")
//                            for (path in split) {
//                                pages.add(Page().apply { this.path = hostPah + path })
//                            }
//                            dataList.value = pages
//                        }
//                    }
//                })
//    }
//
//    fun requestList() {
//        XHttp.getService(ScrectApi::class.java)
//                .chapterList(14389, 1, 10, object : ResponseListener<Any> {
//                    override fun onNext(t: Any?) {
//                        t?.let {
//
//                        }
//                    }
//                })
//    }

    fun requestBookList() {
        if (!hasNext) {
            XToast.error("没有更多了")
        }
        page++
        XHttp.getService(ScrectApi::class.java)
                .newBookList(page, object : ResponseListener<HttpResult<BookList>> {

                    override fun onNext(t: HttpResult<BookList>?) {
                        t?.data?.let {
                            hasNext = !it.lastPage
                            it?.list?.let {
                                dataList.addAll(it)
                            }
                            dataEvent.value = dataList
                        }
                    }
                })
    }

//    fun next() {
//        episodes.takeIf { it.isNotEmpty() }
//                .let {
//                    val data = episodes.get(episodes.size - 1)
//                    AppConfig.saveBookNumber(data.id!!.toInt())
//                    request(data.id!!.toInt())
//                }
//    }
}