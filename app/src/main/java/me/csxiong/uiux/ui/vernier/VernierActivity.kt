package me.csxiong.uiux.ui.vernier

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import me.csxiong.library.base.BaseActivity
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ActivityVernierBinding

@Route(path = "/main/vernier", name = "游标控件")
class VernierActivity : BaseActivity<ActivityVernierBinding>() {

    override fun initView() {
        mViewBinding.vv.onProgressChangeListener = object : OnProgressChangeListener {
            override fun onStartTracking(progress: Int, scrollX: Int) {
            }

            override fun onProgressChange(progress: Int, scrollX: Int, fromUser: Boolean) {
                mViewBinding.tv.setText(progress.toString())
            }

            override fun onStopTracking(progress: Int, scrollX: Int, fromUser: Boolean) {
            }
        }
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_vernier
    }

    fun noAnim(view: View) {
        mViewBinding.vv.setProgress(30, true)
    }

    fun withAnim(view: View) {
        mViewBinding.vv.setProgress(600, true)
    }

}