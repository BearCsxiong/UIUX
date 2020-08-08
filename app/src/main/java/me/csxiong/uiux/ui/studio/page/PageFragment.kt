package me.csxiong.uiux.ui.studio.page

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import me.csxiong.library.base.BaseFragment
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.FragmentPageBinding
import me.csxiong.uiux.ui.studio.selection.SelectionViewModel

class PageFragment : BaseFragment<FragmentPageBinding>() {

    val selectionViewModel by lazy { ViewModelProviders.of(activity!!)[SelectionViewModel::class.java] }

    val mAdapter by lazy { XRecyclerViewAdapter(context) }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_page
    }

    override fun initView() {
        //选中某一话 直接使用html加载漫画内容
        selectionViewModel.applySelectionEvent.observe(activity!!, Observer {
            var sb = StringBuilder().append("<html><body><center>")
            it?.imagelist?.let {
                var splits = it.split(",")
                for (path in splits) {
                    sb.append("<img width=\"100%\" src=\"")
                            .append(me.csxiong.uiux.utils.ImageUtils.getImagePath(path))
                            .append("\"></img>")
                }
            }
            sb.append("</body></html>")
            mViewBinding.web.loadData(sb.toString(), "text/html", "UTF-8")
        })
    }
}