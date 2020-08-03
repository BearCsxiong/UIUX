package me.csxiong.uiux.ui.book;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import me.csxiong.library.base.XActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityScrectBinding;
import me.csxiong.uiux.ui.book.bean.Book;

@Route(path = "/main/book", name = "图书")
public class BookListActivity extends XActivity<ActivityScrectBinding, BookViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_screct;
    }

    XRecyclerViewAdapter mAdapter;

    @Override
    public void initView() {
        mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewBinding.rv.addItemDecoration(new BookItemDecoration());
        mViewBinding.rv.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mViewModel.getDataEvent().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> pages) {
                mAdapter.updateItemEntities(AdapterDataBuilder.create()
                        .addEntities(pages, BookListViewHolder.class)
                        .build());
            }
        });

        mViewModel.requestBookList();
    }
}
