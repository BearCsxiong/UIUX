package me.csxiong.uiux.ui.radar;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.ThreadExecutor;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityRadarBinding;

@Route(path = "/main/radar", name = "雷达图")
public class RadarActivity extends BaseActivity<ActivityRadarBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_radar;
    }

    int i = 1;

    @Override
    public void initView() {
        ThreadExecutor.runOnBackgroundThread(() -> {
            mViewBinding.btn.animate().alpha(.5f)
                    .setDuration(300)
                    .start();
        });
        mViewBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i > 9) {
                    i = 1;
                }
                switch (i) {
                    case 1:
                        mViewBinding.rv.updateData(new float[]{5, 3, 1, 2, 4});
                        break;
                    case 2:
                        mViewBinding.rv.updateData(new float[]{4, 3, 1, 3, 4});
                        break;
                    case 3:
                        mViewBinding.rv.updateData(new float[]{1, 1, 1, 1, 1});
                        break;
                    case 4:
                        mViewBinding.rv.updateData(new float[]{1, 3, 4, 5, 2});
                        break;
                    case 5:
                        mViewBinding.rv.updateData(new float[]{4, 4, 4, 4, 4});
                        break;
                    case 6:
                        mViewBinding.rv.updateData(new float[]{1, 2, 3, 4, 5});
                        break;
                    case 7:
                        mViewBinding.rv.updateData(new float[]{5, 5, 5, 5, 5});
                        break;
                    case 8:
                        mViewBinding.rv.updateData(new float[]{2, 3, 4, 1, 5});
                        break;
                    case 9:
                        mViewBinding.rv.updateData(new float[]{2, 2, 2, 2, 2});
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {

    }
}
