package me.csxiong.uiux.ui.fliper;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityFliperBinding;

/**
 * @Desc : 滚动效果Fliper
 * @Author : Bear - 2020/6/9
 */
@Route(path = "/main/fliper", name = "滚动Fliper")
public class FliperActivity extends BaseActivity<ActivityFliperBinding> {
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_fliper;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
