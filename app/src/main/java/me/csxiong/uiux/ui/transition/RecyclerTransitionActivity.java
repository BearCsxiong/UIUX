package me.csxiong.uiux.ui.transition;

import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.library.utils.XAnimatorCaculateValuer;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityRecyclerTransitionBinding;
import me.csxiong.uiux.ui.color.ColorWheelViewHolder;
import me.csxiong.uiux.ui.layoutManager.FastLinearLayoutManager;

@Route(path = "/main/transition", name = "Recycler动画")
public class RecyclerTransitionActivity extends BaseActivity<ActivityRecyclerTransitionBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_transition;
    }

    XAnimatorCaculateValuer translateY = new XAnimatorCaculateValuer();

    XRecyclerViewAdapter mAdapter;

    @Override
    public void initView() {
        translateY.mark(0, -XDisplayUtil.dpToPx(100));
        mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new FastLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 22, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), ColorWheelViewHolder.class)
                .build());

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                mViewBinding.rv.smoothScrollToPosition(20);
                return false;
            }
        });

        mAdapter.setOnEntityClickListener(new XRecyclerViewAdapter.OnEntityClickListener<Integer>(){
            @Override
            public boolean onClick(int position, Integer entity) {
                mViewBinding.rv.smoothScrollToPosition(position);
                return false;
            }
        },Integer.class);

    }

    @Override
    public void initData() {

    }

    public void onExpand(View v) {
        mViewBinding.rv.stopScroll();
        mViewBinding.rv.smoothScrollToPosition(20);
    }

    public void onShrink(View v) {
        mViewBinding.rv.stopScroll();
        mViewBinding.rv.smoothScrollToPosition(0);
    }
}
