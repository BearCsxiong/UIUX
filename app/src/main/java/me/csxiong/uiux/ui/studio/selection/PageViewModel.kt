package me.csxiong.uiux.ui.studio.selection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import me.csxiong.library.integration.http.ResponseListener
import me.csxiong.library.integration.http.XHttp
import me.csxiong.library.utils.XToast
import me.csxiong.uiux.ui.studio.BookApi
import me.csxiong.uiux.ui.studio.HttpResult
import me.csxiong.uiux.ui.studio.bean.Selection
import me.csxiong.uiux.ui.studio.bean.SelectionList
import me.csxiong.uiux.utils.RefreshState

/**
 * @Desc : 选集ViewModel
 * @Author : Bear - 2020/8/3
 */
class PageViewModel(application: Application) : AndroidViewModel(application) {

    var page = 1

    var pageSize = 1000

    var capterId = 0

    val dataList = ArrayList<Selection>()

    var refreshStateEvent = MutableLiveData<Int>()

    val dataEvent by lazy { MutableLiveData<List<Selection>>() }

    val applySelectionEvent by lazy { MutableLiveData<Selection>() }

    fun refreshSelection() {
        page = 1
        XHttp.getService(BookApi::class.java)
                .chapterList(capterId, page, pageSize, object : ResponseListener<HttpResult<SelectionList>>() {

                    override fun onNext(t: HttpResult<SelectionList>?) {
                        page++
                        refreshStateEvent.value = RefreshState.FINISH_REFRESH
                        t?.data?.let {
                            dataList.clear()
                            it.list?.let {
                                dataList.addAll(it)
                            }
                            dataEvent.value = dataList
                        }
                    }
                })
    }

    fun pre(manhuaId: Int, chapterId: Int) {
        XHttp.getService(BookApi::class.java)
                .chapterPre(manhuaId, chapterId, object : ResponseListener<HttpResult<Selection>>() {

                    override fun onNext(t: HttpResult<Selection>?) {
                        if (t == null) {
                            XToast.error("失败")
                            return
                        }
                        t.data?.let {
                            applySelectionEvent.value = it
                        }
                    }

                })
    }

    fun next(manhuaId: Int, chapterId: Int) {
        XHttp.getService(BookApi::class.java)
                .chapterNext(manhuaId, chapterId, object : ResponseListener<HttpResult<Selection>>() {

                    override fun onNext(t: HttpResult<Selection>?) {
                        if (t == null) {
                            XToast.error("失败")
                            return
                        }
                        t.data?.let {
                            applySelectionEvent.value = it
                        }
                    }

                })
    }

    fun applySelection(selection: Selection) {
        applySelectionEvent.value = selection
    }
}