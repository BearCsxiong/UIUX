package me.csxiong.uiux.ui.studio;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;
import java.util.List;

import me.csxiong.ipermission.EnsureAllPermissionCallBack;
import me.csxiong.ipermission.IPermission;
import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityBookBinding;
import me.csxiong.uiux.utils.StatusBarUtil;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.useImmersiveMode(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        new IPermission(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .excute(new EnsureAllPermissionCallBack() {
                    @Override
                    public void onAllPermissionEnable(boolean isEnable) {

                    }

                    @Override
                    public void onPreRequest(List<String> requestList) {

                    }
                });
        mViewModel = ViewModelProviders.of(this).get(BookStudioViewModel.class);
        fm = getSupportFragmentManager();
        mBottomAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewBinding.rvBottom.setAdapter(mBottomAdapter);

        mBottomAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(BottomFunction.values()), BottomFunctionViewHolder.class)
                .build()
        );

        mBottomAdapter.setOnEntityClickListener(new XRecyclerViewAdapter.OnEntityClickListener<BottomFunction>() {
            @Override
            public boolean onClick(int position, BottomFunction entity) {
                if (mViewModel.isShow(entity)) {
                    mViewModel.show(null);
                } else {
                    mViewModel.show(entity);
                }
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

    private boolean isRead = false;

    @Override
    public void initData() {

    }

}
