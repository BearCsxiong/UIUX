package me.csxiong.uiux.ui.gesture;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.XResUtils;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityGestureBinding;

@Route(path = "/main/gesture", name = "手势View测试界面")
public class GestureViewActivity extends BaseActivity<ActivityGestureBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture;
    }

    @Override
    public void initView() {
        mViewBinding.gv.setImageDrawable(XResUtils.getDrawable(R.mipmap.ic_launcher));
    }

    @Override
    public void initData() {

    }
}
