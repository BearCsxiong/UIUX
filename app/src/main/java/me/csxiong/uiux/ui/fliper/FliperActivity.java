package me.csxiong.uiux.ui.fliper;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.XDisplayUtil;
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
        CircleProgressDrawer circleProgressDrawer = new CircleProgressDrawer(XDisplayUtil.dpToPxInt(50f), XDisplayUtil.dpToPxInt(50f));
        mViewBinding.iv.setImageBitmap(circleProgressDrawer.getCircleProgressBitmap(60));
    }

    @Override
    public void initData() {

    }
}
