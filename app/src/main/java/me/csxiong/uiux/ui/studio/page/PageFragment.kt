package me.csxiong.uiux.ui.studio.page

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import me.csxiong.library.base.BaseFragment
import me.csxiong.library.integration.adapter.AdapterDataBuilder
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.FragmentPageBinding
import me.csxiong.uiux.ui.studio.bean.Page
import me.csxiong.uiux.ui.studio.selection.SelectionViewModel
import me.csxiong.uiux.utils.ImageUtils
import me.csxiong.uiux.utils.print

class PageFragment : BaseFragment<FragmentPageBinding>() {

    val selectionViewModel by lazy { ViewModelProviders.of(activity!!)[SelectionViewModel::class.java] }

    val mAdapter by lazy { XRecyclerViewAdapter(context) }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_page
    }

    override fun initView() {
        mViewBinding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mViewBinding.rv.adapter = mAdapter

        selectionViewModel.applySelectionEvent.observe(activity!!, Observer {
            var array = ArrayList<Page>()
            it?.imagelist?.let {
                var splits = it.split(",")
                for (path in splits) {
                    path.print("csx")
                    array.add(Page().apply { this.path = ImageUtils.getImagePath(path) })
                }
            }

            mAdapter.updateItemEntities(AdapterDataBuilder.create()
                    .addEntities(array, PageViewHolder::class.java)
                    .build())
        })
    }
}