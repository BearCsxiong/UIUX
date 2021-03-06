package me.csxiong.uiux.ui.studio.page

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.csxiong.library.base.BaseFragment
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.FragmentPageBinding
import me.csxiong.uiux.ui.studio.book.BookViewModel
import me.csxiong.uiux.ui.studio.selection.PageViewModel
import me.csxiong.uiux.utils.ImageUtils
import me.csxiong.uiux.utils.print
import kotlin.math.log

class PageFragment : BaseFragment<FragmentPageBinding>() {

    val selectionViewModel by lazy { ViewModelProviders.of(activity!!)[PageViewModel::class.java] }

    val bookViewModel by lazy { ViewModelProviders.of(activity!!)[BookViewModel::class.java] }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_page
    }

    override fun initView() {
        //选中某一话 直接使用html加载漫画内容
        selectionViewModel.applySelectionEvent.observe(activity!!, Observer {
            var sb = StringBuilder().append("<html>")
//                    .append("<head>")
//                    .append("" +
//                            "")
//                    .append("</head>")
                    .append("<body><center>")
            it?.imagelist?.let {
                var splits = it.split(",")
                for (path in splits) {
                    Log.e("csx",ImageUtils.getImagePath(path))
                    sb.append("<img width=\"100%\" src=\"")
                            .append(ImageUtils.getImagePath(path))
                            .append("\"></img>")
                    "${ImageUtils.getImagePath(path)}".print("csx")
                }
            }
            sb.append("</body></html>")
            mViewBinding.web.loadData(sb.toString(), "text/html", "UTF-8")
//            mViewBinding.web.loadData(sb.toString(), "text/html", "UTF-8")
        })

        mViewBinding.tvPre.setOnClickListener {
            bookViewModel.selectBookEvent.value?.let { book ->
                selectionViewModel.applySelectionEvent.value?.let { selection ->
                    selectionViewModel.pre(book.id, selection.id!!.toInt())
                }
            }
        }

        mViewBinding.tvNext.setOnClickListener {
            bookViewModel.selectBookEvent.value?.let { book ->
                selectionViewModel.applySelectionEvent.value?.let { selection ->
                    selectionViewModel.next(book.id, selection.id!!.toInt())
                }
            }
        }
    }
}