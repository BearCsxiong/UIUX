package me.csxiong.uiux.ui.studio.book

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import me.csxiong.library.integration.http.ResponseListener
import me.csxiong.library.integration.http.XHttp
import me.csxiong.library.utils.XToast
import me.csxiong.uiux.ui.studio.BookApi
import me.csxiong.uiux.ui.studio.HttpResult
import me.csxiong.uiux.ui.studio.bean.Book
import me.csxiong.uiux.ui.studio.bean.BookList
import me.csxiong.uiux.utils.RefreshState

class BookViewModel  constructor(application: Application) : AndroidViewModel(application) {

    var dataList = ArrayList<Book>()

    var dataEvent = MutableLiveData<List<Book>>()

    var refreshStateEvent = MutableLiveData<Int>()

    var page = 0

    var hasNext = true

    val selectBookEvent = MutableLiveData<Book>()

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

    fun select(book: Book) {
        selectBookEvent.value = book
    }

    fun refreshBookList() {
        hasNext = true
        page = 1
        XHttp.getService(BookApi::class.java)
                .newBookList(page, object : ResponseListener<HttpResult<BookList>>() {

                    override fun onNext(t: HttpResult<BookList>?) {
                        refreshStateEvent.value = RefreshState.COMPLETE
                        t?.data?.let {
                            hasNext = !it.lastPage
                            dataList.clear()
                            it.list?.let {
                                dataList.addAll(it)
                            }
                            dataEvent.value = dataList
                        }
                    }
                })
    }

    fun loadMoreBookList() {
        if (!hasNext) {
            XToast.error("没有更多了")
            refreshStateEvent.value = RefreshState.COMPLETE
        }
        page++
        XHttp.getService(BookApi::class.java)
                .newBookList(page, object : ResponseListener<HttpResult<BookList>>() {

                    override fun onNext(t: HttpResult<BookList>?) {
                        refreshStateEvent.value = RefreshState.COMPLETE
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