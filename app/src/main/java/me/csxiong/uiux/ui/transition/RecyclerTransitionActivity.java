package me.csxiong.uiux.ui.transition;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.integration.adapter.AdapterDataBuilder;
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter;
import me.csxiong.library.utils.XAnimator;
import me.csxiong.library.utils.XAnimatorCaculateValuer;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityRecyclerTransitionBinding;
import me.csxiong.uiux.ui.color.ColorWheelViewHolder;

@Route(path = "/main/transition", name = "Recycler动画")
public class RecyclerTransitionActivity extends BaseActivity<ActivityRecyclerTransitionBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_transition;
    }

    XAnimatorCaculateValuer translateY = new XAnimatorCaculateValuer();

    private XAnimator xAnimator = XAnimator.ofFloat(0, 1f)
            .duration(300)
            .setAnimationListener(new XAnimator.XAnimationListener() {
                @Override
                public void onAnimationUpdate(float fraction, float value) {
                    if (isExpand) {
                        mAdapter.notifyAllItemChanged(fraction);
                        mViewBinding.rv.setTranslationY(translateY.caculateValue(fraction));
                    } else {
                        mAdapter.notifyAllItemChanged(1 - fraction);
                        mViewBinding.rv.setTranslationY(translateY.caculateValue(1 - fraction));
                    }
                }

                @Override
                public void onAnimationStart(XAnimator animation) {

                }

                @Override
                public void onAnimationEnd(XAnimator animation) {

                }

                @Override
                public void onAnimationCancel(XAnimator animation) {

                }
            });

    XRecyclerViewAdapter mAdapter;

    @Override
    public void initView() {
        translateY.mark(0, -XDisplayUtil.dpToPx(100));
        mAdapter = new XRecyclerViewAdapter(this);
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2), ColorWheelViewHolder.class)
                .build());

    }

    @Override
    public void initData() {

    }

    private boolean isExpand = true;

    public void onExpand(View v) {
        if (isExpand) {
            return;
        }
        isExpand = true;
        xAnimator.cancel();
        xAnimator.start();
    }

    public void onShrink(View v) {
        if (!isExpand) {
            return;
        }
        isExpand = false;
        xAnimator.cancel();
        xAnimator.start();
    }
}
