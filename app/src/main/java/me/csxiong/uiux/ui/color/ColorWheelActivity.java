package me.csxiong.uiux.ui.color;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityColorWheelBinding;
import me.csxiong.uiux.ui.layoutManager.active.ActiveScrollListener;
import me.csxiong.uiux.ui.layoutManager.active.CenterActiveStrategy;

@Route(path = "/main/color/wheel", name = "色轮")
public class ColorWheelActivity extends BaseActivity<ActivityColorWheelBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_color_wheel;
    }

    @Override
    public void initView() {
        XRecyclerViewAdapter mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), ColorWheelViewHolder.class)
                .build());

        mViewBinding.rv.addOnScrollListener(new ActiveScrollListener(mViewBinding.rv, new CenterActiveStrategy(true), false) {
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
                RecyclerView.ViewHolder holder = mViewBinding.rv.findViewHolderForLayoutPosition(position);
                if (holder instanceof ColorWheelViewHolder) {
                    holder.itemView.animate().cancel();
                    holder.itemView.animate().scaleX(1.5f)
                            .scaleY(1.5f)
                            .setDuration(200)
                            .start();
                }
            }

            @Override
            public void onDeActive(int position) {
                RecyclerView.ViewHolder holder = mViewBinding.rv.findViewHolderForLayoutPosition(position);
                if (holder instanceof ColorWheelViewHolder) {
                    holder.itemView.animate().cancel();
                    holder.itemView.animate().scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200)
                            .start();
                }
            }

        });
    }

    @Override
    public void initData() {

    }
}
