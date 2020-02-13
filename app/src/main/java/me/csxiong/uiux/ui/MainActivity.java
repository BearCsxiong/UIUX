package me.csxiong.uiux.ui;

import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import me.csxiong.library.base.XActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityMainBinding;

/**
 * @Desc : 主页
 * @Author : csxiong - 2019-11-13
 */
@Route(path = "/app/main", name = "主页")
public class MainActivity extends XActivity<ActivityMainBinding, MainViewModel> {

    private XRecyclerViewAdapter mAdapter;

    private List<FeatureBean> mDataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rv.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mDataList.add(new FeatureBean("相册页面", "/camera/album"));
        mDataList.add(new FeatureBean("多人拍照的手势控件", "/main/capture"));
        mDataList.add(new FeatureBean("新多人拍照的手势控件", "/main/new/capture"));
        mDataList.add(new FeatureBean("多边形", "/main/polygon"));
        mDataList.add(new FeatureBean("雷达图", "/main/radar"));
        mDataList.add(new FeatureBean("SeekBar测试", "/main/seekbar"));
        mDataList.add(new FeatureBean("手势View测试", "/main/gesture"));
        mDataList.add(new FeatureBean("DataMask测试", "/main/data/mask"));
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(mDataList, FeatureViewHolder.class).build());

        mAdapter.setOnItemChildClickListener((position, item, view) -> {
            Object entity = item.getEntity();
            if (entity instanceof FeatureBean) {
                ARouter.getInstance()
                        .build(((FeatureBean) entity).getRoute())
                        .navigation(this);
            }
        });
    }

}
