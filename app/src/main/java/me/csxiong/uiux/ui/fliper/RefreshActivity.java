package me.csxiong.uiux.ui.fliper;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityFliperBinding;
import me.csxiong.uiux.ui.color.ColorWheelViewHolder;
import me.csxiong.uiux.ui.layoutManager.FastLinearLayoutManager;

/**
 * @Desc : 滚动效果Fliper
 * @Author : Bear - 2020/6/9
 */
@Route(path = "/main/fliper", name = "滚动Fliper")
public class RefreshActivity extends BaseActivity<ActivityFliperBinding> {

    XRecyclerViewAdapter mAdapter;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_fliper;
    }

    @Override
    public void initView() {
//        CircleProgressDrawer circleProgressDrawer = new CircleProgressDrawer(XDisplayUtil.dpToPxInt(50f), XDisplayUtil.dpToPxInt(50f));
//        mViewBinding.iv.setImageBitmap(circleProgressDrawer.getCircleProgressBitmap(60));
        mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new FastLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 22, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), ColorWheelViewHolder.class)
                .build());

//        mViewBinding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                XToast.success("刷新");
//                ThreadExecutor.runOnUiThread(()->{
//                    mViewBinding.refresh.finishRefresh();
//                },3000);
//            }
//
//            @Override
//            public void onLoadMore() {
//                XToast.success("刷新");
//                ThreadExecutor.runOnUiThread(()->{
//                    mViewBinding.refresh.finishLoadMore();
//                },3000);
//            }
//        });
    }

    @Override
    public void initData() {

    }
}
