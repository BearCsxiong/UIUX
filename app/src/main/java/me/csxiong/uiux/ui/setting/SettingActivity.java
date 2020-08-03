package me.csxiong.uiux.ui.setting;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.uiux.BuildConfig;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivitySettingBinding;

@Route(path = "/main/setting", name = "设置")
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    int times = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mViewBinding.tv.setText("Version\t:\t" + BuildConfig.VERSION_NAME);
    }

    @Override
    public void initData() {
        mViewBinding.tv.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build("/main/book")
                    .navigation(this);
            finish();
        });
    }
}
