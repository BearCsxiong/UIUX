package me.csxiong.uiux.ui.studio.book

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import me.csxiong.library.base.BaseFragment
import me.csxiong.library.integration.adapter.AdapterDataBuilder
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.FragmentBookBinding
import me.csxiong.uiux.ui.studio.BookItemDecoration
import me.csxiong.uiux.ui.studio.BookStudioViewModel
import me.csxiong.uiux.ui.studio.BookViewHolder
import me.csxiong.uiux.ui.studio.BottomFunction
import me.csxiong.uiux.ui.studio.bean.Book
import me.csxiong.uiux.utils.RefreshState

/**
 * @Desc : Book的Fragment
 * @Author : Bear - 2020/8/3
 */
class BookFragment : BaseFragment<FragmentBookBinding>() {

    val mAdapter by lazy { XRecyclerViewAdapter(context) }

    val mViewModel by lazy { ViewModelProviders.of(activity!!)[BookViewModel::class.java] }

    val bookStudioViewModel by lazy { ViewModelProviders.of(activity!!)[BookStudioViewModel::class.java] }

    override fun initData() {
        mViewModel.refreshBookList()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_book
    }

    override fun initView() {

        mViewBinding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mViewBinding.rv.addItemDecoration(BookItemDecoration())
        mViewBinding.rv.adapter = mAdapter

        mViewBinding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.loadMoreBookList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.refreshBookList()
            }
        })

        mAdapter.setOnEntityClickListener({ position, entity ->
            mViewModel.select(entity)
            bookStudioViewModel.show(BottomFunction.Selection)
            false
        }, Book::class.java)

        mViewModel.dataEvent.observe(this, Observer<List<Book>?> { pages ->
            mAdapter.updateItemEntities(AdapterDataBuilder.create()
                    .addEntities(pages, BookViewHolder::class.java)
                    .build())
        })

        mViewModel.refreshStateEvent.observe(this, Observer<Int?> { integer ->
            if (integer == RefreshState.COMPLETE) {
                mViewBinding.refresh.finishRefresh()
                mViewBinding.refresh.finishLoadMore()
            }
        })

        mViewBinding.ivClose.setOnClickListener {
            bookStudioViewModel.show(null)
        }
    }


}