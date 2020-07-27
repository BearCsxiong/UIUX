package me.csxiong.uiux.ui.disseek;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityDisSeekBinding;

/**
 * @Desc : DisSeekActivity
 * @Author : Bear - 2020/7/22
 */
@Route(path = "/main/disseek",name = "不规则Seek")
public class DisSeekActivity extends BaseActivity<ActivityDisSeekBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_dis_seek;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
