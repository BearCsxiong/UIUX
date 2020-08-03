package me.csxiong.uiux.ui.studio.selection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import me.csxiong.library.base.BaseFragment
import me.csxiong.library.integration.adapter.AdapterDataBuilder
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.FragmentSelectionBinding
import me.csxiong.uiux.ui.studio.BookStudioViewModel
import me.csxiong.uiux.ui.studio.bean.Selection
import me.csxiong.uiux.ui.studio.book.BookViewModel

/**
 * @Desc : 选集界面
 * @Author : Bear - 2020/8/3
 */
class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {

    val mViewModel by lazy { ViewModelProviders.of(activity!!)[SelectionViewModel::class.java] }

    val bookViewModel by lazy { ViewModelProviders.of(activity!!)[BookViewModel::class.java] }

    val bookStudioViewModel by lazy { ViewModelProviders.of(activity!!)[BookStudioViewModel::class.java] }

    val mAdapter by lazy { XRecyclerViewAdapter(context) }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_selection
    }

    override fun initView() {
        mViewBinding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mViewBinding.rv.adapter = mAdapter

        bookViewModel.selectBookEvent.observe(activity!!, Observer {
            it?.let {
                mViewModel.capterId = it.id
                mViewModel.refreshSelection()
            }
        })

        mViewModel.dataEvent.observe(activity!!, Observer {
            mAdapter.updateItemEntities(AdapterDataBuilder.create()
                    .addEntities(it, SelectionViewHolder::class.java)
                    .build())
        })

        mViewModel.refreshStateEvent.observe(activity!!, Observer {
            mViewBinding.refresh.finishRefresh()
            mViewBinding.refresh.finishLoadMore()
        })

        mViewBinding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.loadMoreSelection()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {

            }
        })

        mAdapter.setOnEntityClickListener(object : XRecyclerViewAdapter.OnEntityClickListener<Selection> {
            override fun onClick(position: Int, entity: Selection?): Boolean {
                entity?.let {
                    mViewModel.applySelection(it)
                    bookStudioViewModel.show(null)
                }
                return false
            }
        }, Selection::class.java)


    }
}