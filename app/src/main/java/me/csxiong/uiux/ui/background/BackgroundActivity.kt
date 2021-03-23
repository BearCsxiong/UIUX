package me.csxiong.uiux.ui.background

import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import me.csxiong.library.base.BaseActivity
import me.csxiong.library.integration.adapter.AdapterDataBuilder
import me.csxiong.library.integration.adapter.XRecyclerViewAdapter
import me.csxiong.library.utils.XResUtils
import me.csxiong.uiux.R
import me.csxiong.uiux.databinding.ActivityBackgroundBinding
import me.csxiong.uiux.ui.capture.CaptureView
import me.csxiong.uiux.ui.seek.XSeekBar

/**
 * @Desc : 背景控件
 * @Author : meitu - 3/16/21
 */
@Route(path = "/main/background", name = "背景控件")
class BackgroundActivity : BaseActivity<ActivityBackgroundBinding>() {

    val horizotalAdapter by lazy { XRecyclerViewAdapter(this) }
    val verticalAdapter by lazy { XRecyclerViewAdapter(this) }
    val raderAdapter by lazy { XRecyclerViewAdapter(this) }
    val sweepAdapter by lazy { XRecyclerViewAdapter(this) }
    val backgroundAdapter by lazy { XRecyclerViewAdapter(this) }
    val xferModeAdapter by lazy { XRecyclerViewAdapter(this) }
    val backgrounds = arrayListOf<Int>(
            R.mipmap.b1,
            R.mipmap.b2,
            R.mipmap.b3,
            R.mipmap.b4,
            R.mipmap.b5,
            R.mipmap.b6,
            R.mipmap.b7,
            R.mipmap.b8,
            R.mipmap.b9,
            R.mipmap.b10,
            R.mipmap.b11,
            R.mipmap.b12,
            R.mipmap.b13,
            R.mipmap.b14,
            R.mipmap.b15
    )

    val backgroundViewModel by lazy { ViewModelProvider(this)[BackgroundViewModel::class.java] }

    override fun getLayoutId(): Int {
        return R.layout.activity_background
    }

    override fun initView() {
        mViewBinding.horizontalRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.verticalRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.raderRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.sweepRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.backgroundRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.blendRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.horizontalRv.adapter = horizotalAdapter
        mViewBinding.verticalRv.adapter = verticalAdapter
        mViewBinding.raderRv.adapter = raderAdapter
        mViewBinding.sweepRv.adapter = sweepAdapter
        mViewBinding.backgroundRv.adapter = backgroundAdapter
        mViewBinding.blendRv.adapter = xferModeAdapter

        horizotalAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(arrayListOf(
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xff00c3ff.toInt(), 0xffffff1c.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xff5614B0.toInt(), 0xffDBD65C.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xfff3904f.toInt(), 0xffD3b4371.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xff403b4a.toInt(), 0xffe7e9bb.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xffC6FFDD.toInt(), 0xffFBD786.toInt(), 0xffF7797D.toInt()), floatArrayOf(0.01f, 0.5f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xff1A2A6C.toInt(), 0xffB21F1F.toInt(), 0xffFBDD2D.toInt()), floatArrayOf(0.01f, 0.5f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 0, intArrayOf(0xff3A1C71.toInt(), 0xffD76D77.toInt(), 0xffFFAF7B.toInt()), floatArrayOf(0.01f, 0.5f, 1f)),
                        BackgroundType(BackgroundType.Type.HORIZONTAL, 45, intArrayOf(0xff03001E.toInt(), 0xff7303C0.toInt(), 0xffF64F59.toInt(), 0xffFDEFF9.toInt()), floatArrayOf(0f, 0.36f, 0.68f, 1f))
                ), GradientViewHolder::class.java)
                .build())

        verticalAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(arrayListOf(
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xfffad0c4.toInt(), 0xffffd1ff.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xffd299c2.toInt(), 0xfffef9e7.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xfffbc2eb.toInt(), 0xffa6c1ee.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xffe8198b.toInt(), 0xffc7eafd.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xfffa709a.toInt(), 0xfffee140.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xffabecd6.toInt(), 0xfffbed96.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff84fab0.toInt(), 0xff8fd3f4.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff64b3f4.toInt(), 0xffc3e59c.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff93a5cf.toInt(), 0xffe4efe9.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff13547a.toInt(), 0xff80d0c7.toInt()), floatArrayOf(0f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff0c3483.toInt(), 0xffa2b6df.toInt(), 0xff6b8cce.toInt()), floatArrayOf(0f, 0.5f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xff2CD8D5.toInt(), 0xffC5C1FF.toInt(), 0xffFFBAC3.toInt()), floatArrayOf(0f, 0.56f, 1f)),
                        BackgroundType(BackgroundType.Type.VERTICAL, 0, intArrayOf(0xffFF057C.toInt(), 0xff7C64D5.toInt(), 0xff4CC3FF.toInt()), floatArrayOf(0f, 0.48f, 1f))
                ), GradientViewHolder::class.java)
                .build())

        raderAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(arrayListOf(
                        BackgroundType(BackgroundType.Type.RADIAL, 0, intArrayOf(0xffFFBAC3.toInt(), 0xffC5C1FF.toInt(), 0xff2CD8D5.toInt()), floatArrayOf(0f, 0.56f, 1f))
                ), GradientViewHolder::class.java)
                .build())

        sweepAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(arrayListOf(
                        BackgroundType(BackgroundType.Type.SWEEP, 135, intArrayOf(0xff2CD8D5.toInt(), 0xffC5C1FF.toInt(), 0xffFFBAC3.toInt()), floatArrayOf(0f, 0.56f, 1f))
                ), GradientViewHolder::class.java)
                .build())

        backgroundAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(backgrounds, BackgroundViewHolder::class.java)
                .build())

        xferModeAdapter.updateItemEntities(AdapterDataBuilder
                .create()
                .addEntities(PorterDuff.Mode.values().asList(), XferModeViewHolder::class.java)
                .build())

        horizotalAdapter.setOnEntityClickListener(BackgroundType::class.java) { position, entity ->
            backgroundViewModel.applyGradientType.value = entity
            horizotalAdapter.currentSelectEntity = entity
            verticalAdapter.currentSelectEntity = null
            raderAdapter.currentSelectEntity = null
            sweepAdapter.currentSelectEntity = null
            false
        }
        verticalAdapter.setOnEntityClickListener(BackgroundType::class.java) { position, entity ->
            backgroundViewModel.applyGradientType.value = entity
            horizotalAdapter.currentSelectEntity = null
            verticalAdapter.currentSelectEntity = entity
            raderAdapter.currentSelectEntity = null
            sweepAdapter.currentSelectEntity = null
            false
        }
        raderAdapter.setOnEntityClickListener(BackgroundType::class.java) { position, entity ->
            backgroundViewModel.applyGradientType.value = entity
            horizotalAdapter.currentSelectEntity = null
            verticalAdapter.currentSelectEntity = null
            raderAdapter.currentSelectEntity = entity
            sweepAdapter.currentSelectEntity = null
            false
        }
        sweepAdapter.setOnEntityClickListener(BackgroundType::class.java) { position, entity ->
            backgroundViewModel.applyGradientType.value = entity
            horizotalAdapter.currentSelectEntity = null
            verticalAdapter.currentSelectEntity = null
            raderAdapter.currentSelectEntity = null
            sweepAdapter.currentSelectEntity = entity
            false
        }

        backgroundAdapter.setOnEntityClickListener(Integer::class.java) { position, entity ->
            val bitmap = (XResUtils.getDrawable(entity as Int))
            bitmap.alpha = 125
            backgroundViewModel.applyBackgroundType.value = (bitmap as BitmapDrawable).bitmap
            backgroundAdapter.currentSelectEntity = entity
            false
        }

        xferModeAdapter.setOnEntityClickListener(PorterDuff.Mode::class.java) { position, entity ->
            backgroundViewModel.applyMode.value = entity
            xferModeAdapter.currentSelectEntity = entity
            false
        }

        backgroundViewModel.applyBackgroundType.observe(this, Observer {
            mViewBinding.bv.setMixture(it, backgroundViewModel.applyGradientType.value, backgroundViewModel.applyMode.value
                    ?: PorterDuff.Mode.OVERLAY)
        })

        backgroundViewModel.applyGradientType.observe(this, Observer {
            mViewBinding.bv.setMixture(backgroundViewModel.applyBackgroundType.value, it, backgroundViewModel.applyMode.value
                    ?: PorterDuff.Mode.OVERLAY)
        })

        backgroundViewModel.applyMode.observe(this, Observer {
            mViewBinding.bv.setMixture(backgroundViewModel.applyBackgroundType.value, backgroundViewModel.applyGradientType.value, it)
        })

        val drawable = (XResUtils.getDrawable(R.mipmap.background) as BitmapDrawable).bitmap
        mViewBinding.bv.setMixture(drawable, BackgroundType(BackgroundType.Type.SWEEP, 0, intArrayOf(0xff2CD8D5.toInt(), 0xffC5C1FF.toInt(), 0xffFFBAC3.toInt()), floatArrayOf(0f, 0.56f, 1f)))

        mViewBinding.xsb.maxProgress = 255
        mViewBinding.xsb.minProgress = 0
        mViewBinding.xsb.setProgress(255)
        mViewBinding.xsb.setOnProgressChangeListener(object : XSeekBar.OnProgressChangeListener {
            override fun onProgressChange(progress: Int, leftDx: Float, fromUser: Boolean) {
                super.onProgressChange(progress, leftDx, fromUser)
                if (fromUser) {
                    mViewBinding.bv.setAlpha(progress)
                }
            }
        })
    }

    override fun initData() {
    }
}