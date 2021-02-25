package me.csxiong.uiux.ui.guide

import com.alibaba.android.arouter.facade.annotation.Route
import me.csxiong.library.base.BaseActivity
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ActivityGuideMaskBinding

@Route(path = "/main/guide", name = "引导抠图")
class GuideMaskActivity : BaseActivity<ActivityGuideMaskBinding>() {

    override fun initView() {
        mViewBinding.gmv.clip(mViewBinding.v)
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_guide_mask
    }
}