package me.csxiong.uiux.ui.studio;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityBookBinding;

/**
 * 主要的书本界面
 */
@Route(path = "/main/book", name = "图书")
public class BookStudioActivity extends BaseActivity<ActivityBookBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_book;
    }

    private BookStudioViewModel mViewModel;

    private XRecyclerViewAdapter mBottomAdapter;

    private Fragment curBottomFragment;

    private FragmentManager fm;

    @Override
    public void initView() {
        mViewModel = ViewModelProviders.of(this).get(BookStudioViewModel.class);
        fm = getSupportFragmentManager();
        mBottomAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mViewBinding.rvBottom.setAdapter(mBottomAdapter);

        mBottomAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(BottomFunction.values()), BottomFunctionViewHolder.class)
                .build()
        );

        mBottomAdapter.setOnEntityClickListener(new XRecyclerViewAdapter.OnEntityClickListener<BottomFunction>() {
            @Override
            public boolean onClick(int position, BottomFunction entity) {
                mViewModel.show(entity);
                return false;
            }
        }, BottomFunction.class);

        mViewModel.getBottomFunctionChangeEvent().observe(this, new Observer<BottomFunction>() {
            @Override
            public void onChanged(@Nullable BottomFunction bottomFunction) {
                show(bottomFunction);
            }
        });
    }

    public void show(BottomFunction bottomFunction) {
        hideAllBottom();
        if (bottomFunction != null) {
            Fragment fg = createOrFind(bottomFunction);
            if (fg != null) {
                if (fg.isAdded()) {
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.beauty_bottom_menu_up, R.anim.beauty_bottom_menu_down)
                            .show(fg)
                            .commitAllowingStateLoss();
                } else {
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.beauty_bottom_menu_up, R.anim.beauty_bottom_menu_down)
                            .add(R.id.fl_bottom, fg, bottomFunction.getTag())
                            .commitAllowingStateLoss();
                }
                curBottomFragment = fg;
            }
        }
    }

    public void hideAllBottom() {
        if (curBottomFragment != null) {
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.beauty_bottom_menu_up, R.anim.beauty_bottom_menu_down)
                    .hide(curBottomFragment)
                    .commitAllowingStateLoss();
            curBottomFragment = null;
        }
    }

    public Fragment createOrFind(BottomFunction bottomFunction) {
        Fragment fg = fm.findFragmentByTag(bottomFunction.getTag());
        if (fg == null) {
            try {
                fg = bottomFunction.getFgClass().newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return fg;
    }

    @Override
    public void initData() {

    }
}
