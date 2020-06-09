package me.csxiong.uiux.ui.gradient;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityGradientBinding;

@Route(path = "/main/gradient", name = "渐变顶部栏")
public class GradientViewAcitivity extends BaseActivity<ActivityGradientBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_gradient;
    }

    @Override
    public void initView() {
        mViewBinding.gv.start();
        mViewBinding.gvCenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding.gv.destroy();
        mViewBinding.gvCenter.destroy();
    }

    @Override
    public void initData() {

    }
}
