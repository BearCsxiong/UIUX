package me.csxiong.uiux.ui.studio.selection

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import me.csxiong.library.base.XViewModel
import me.csxiong.uiux.ui.http.HttpResult
import me.csxiong.uiux.ui.http.ResponseListener
import me.csxiong.uiux.ui.http.XHttp
import me.csxiong.uiux.ui.http.api.BookApi
import me.csxiong.uiux.ui.studio.bean.Selection
import me.csxiong.uiux.ui.studio.bean.SelectionList
import me.csxiong.uiux.utils.RefreshState

/**
 * @Desc : 选集ViewModel
 * @Author : Bear - 2020/8/3
 */
class SelectionViewModel(application: Application) : XViewModel(application) {

    var page = 1

    var pageSize = 10

    var capterId = 0

    var hasNext = true

    val dataList = ArrayList<Selection>()

    var refreshStateEvent = MutableLiveData<Int>()

    val dataEvent by lazy { MutableLiveData<List<Selection>>() }

    val applySelectionEvent by lazy { MutableLiveData<Selection>() }

    fun refreshSelection() {
        hasNext = true
        page = 1
        XHttp.getService(BookApi::class.java)
                .chapterList(capterId, page, pageSize, object : ResponseListener<HttpResult<SelectionList>> {

                    override fun onNext(t: HttpResult<SelectionList>?) {
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

    fun loadMoreSelection() {
        if (!hasNext) {
            refreshStateEvent.value = RefreshState.COMPLETE
            return
        }
        page++
        XHttp.getService(BookApi::class.java)
                .chapterList(capterId, page, pageSize, object : ResponseListener<HttpResult<SelectionList>> {

                    override fun onNext(t: HttpResult<SelectionList>?) {
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

    fun applySelection(selection: Selection) {
        applySelectionEvent.value = selection
    }
}