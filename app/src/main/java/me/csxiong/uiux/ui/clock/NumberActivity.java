package me.csxiong.uiux.ui.clock;

import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.VibratorUtils;
import me.csxiong.library.utils.XToast;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityCaptureBinding;
import me.csxiong.uiux.databinding.ActivityNumberBinding;

@Route(path = "/main/number", name = "数字控件")
public class NumberActivity extends BaseActivity<ActivityNumberBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_number;
    }

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mViewBinding.bnv.setNumber(6);
        mViewBinding.dv.setNumber(6);
    }

    @Override
    public void initData() {

    }
}
