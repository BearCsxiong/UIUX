package me.csxiong.uiux.ui

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import me.csxiong.library.base.BaseActivity
import me.csxiong.library.integration.adapter.AdapterDataBuilder
import me.csxiong.library.integration.adapter.OnItemChildClickListener
import me.csxiong.library.integration.adapter.XItem
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ActivityMainBinding
import java.util.*

/**
 * @Desc : 主页
 * @Author : csxiong - 2019-11-13
 */
@Route(path = "/app/main", name = "主页")
class MainActivity : BaseActivity<ActivityMainBinding?>(){
    private var mAdapter: XRecyclerViewAdapter? = null
    private val mDataList: MutableList<FeatureBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mAdapter = XRecyclerViewAdapter(this)
        mViewBinding!!.rv.layoutManager = LinearLayoutManager(this)
        mViewBinding!!.rv.adapter = mAdapter
    }

    override fun initData() {
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mDataList.add(FeatureBean("相册页面", "/camera/album"))
        mDataList.add(FeatureBean("多人拍照的手势控件", "/main/capture"))
        mDataList.add(FeatureBean("新多人拍照的手势控件", "/main/new/capture"))
        mDataList.add(FeatureBean("多边形", "/main/polygon"))
        mDataList.add(FeatureBean("雷达图", "/main/radar"))
        mDataList.add(FeatureBean("SeekBar测试", "/main/seekbar"))
        mDataList.add(FeatureBean("手势View测试", "/main/gesture"))
        mDataList.add(FeatureBean("DataMask测试", "/main/data/mask"))
        mDataList.add(FeatureBean("高斯模糊图片测试", "/main/blur"))
        mDataList.add(FeatureBean("渐变顶部栏", "/main/gradient"))
        mDataList.add(FeatureBean("NestedScrollListener", "/main/fliper"))
        mDataList.add(FeatureBean("色轮", "/main/color/wheel"))
        mDataList.add(FeatureBean("Recycler", "/main/transition"))
        mDataList.add(FeatureBean("数字", "/main/number"))
        mDataList.add(FeatureBean("游标控件", "/main/vernier"))
        mDataList.add(FeatureBean("设置", "/main/book"))
        mAdapter!!.updateItemEntities(AdapterDataBuilder.create()
                .addEntities(mDataList, FeatureViewHolder::class.java).build())
        mAdapter!!.onItemChildClickListener = OnItemChildClickListener<FeatureBean> { position: Int, item: XItem<FeatureBean>, view: View? ->
            val entity = item.entity
            if (entity is FeatureBean) {
                ARouter.getInstance()
                        .build(entity.route)
                        .navigation(this)
            }
        }

        mainViewModel.main()

    }
}