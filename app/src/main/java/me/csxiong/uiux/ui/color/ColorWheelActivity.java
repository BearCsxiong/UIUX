package me.csxiong.uiux.ui.color;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.commsource.util.scroll.ActiveScrollListener;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityColorWheelBinding;
import me.csxiong.uiux.ui.layoutManager.CenterScrollLayoutManager;
import me.csxiong.uiux.ui.layoutManager.active.CenterCrossActiveStrategy;

@Route(path = "/main/color/wheel", name = "色轮")
public class ColorWheelActivity extends BaseActivity<ActivityColorWheelBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_color_wheel;
    }

    @Override
    public void initView() {
        XRecyclerViewAdapter mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new CenterScrollLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), ColorWheelViewHolder.class)
                .build());

        mAdapter.setOnEntityClickListener(new XRecyclerViewAdapter.OnEntityClickListener<Integer>() {
            @Override
            public boolean onClick(int position, Integer entity) {
                mViewBinding.rv.smoothScrollToPosition(position);
                return false;
            }
        }, Integer.class);

        mViewBinding.rv.addOnScrollListener(new ActiveScrollListener(mViewBinding.rv, new CenterCrossActiveStrategy(true, XDisplayUtil.dpToPx(50f)), false) {
            @Override
            public View getTargetView(int position) {
                RecyclerView.ViewHolder holder = mViewBinding.rv.findViewHolderForLayoutPosition(position);
                if (holder instanceof ColorWheelViewHolder) {
                    return holder.itemView;
                }
                return null;
            }

            @Override
            public void onActive(int position) {
            }

            @Override
            public void onDeActive(int position) {
            }

            @Override
            public void onPositionPercentChange(int position, float crossPercent) {
                super.onPositionPercentChange(position, crossPercent);
                Log.e("csx",position + "---" + crossPercent);
                RecyclerView.ViewHolder holder = mViewBinding.rv.findViewHolderForLayoutPosition(position);
                if (holder instanceof ColorWheelViewHolder) {
                    ((ColorWheelViewHolder) holder).updateFraction(crossPercent);
                }
            }

            @Override
            public void onScrollStop(int position) {
                super.onScrollStop(position);
                mViewBinding.rv.smoothScrollToPosition(position);
            }
        });
    }

    @Override
    public void initData() {

    }
}
