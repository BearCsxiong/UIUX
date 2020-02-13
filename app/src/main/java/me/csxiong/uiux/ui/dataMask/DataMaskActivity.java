package me.csxiong.uiux.ui.dataMask;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityDataMaskBinding;

@Route(path = "/main/data/mask", name = "数据蒙版")
public class DataMaskActivity extends BaseActivity<ActivityDataMaskBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_data_mask;
    }

    @Override
    public void initView() {
        mViewBinding.iv.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mViewBinding.dm.getCurrentMaskType())) {
            mViewBinding.dm.hideAll();
            return;
        }
        super.onBackPressed();

    }

    public void onEmpty(View view) {
        mViewBinding.dm.showMaskOnly(MaskType.EMPTY);
    }

    public void onError(View view) {
        mViewBinding.dm.showMaskOnly(MaskType.ERROR);
    }

    public void onLoading(View view) {
        mViewBinding.dm.showMaskOnly(MaskType.LOADING);
    }
}
