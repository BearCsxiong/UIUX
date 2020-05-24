package me.csxiong.uiux.ui.blur;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.APP;
import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.ThreadExecutor;
import me.csxiong.library.utils.XResUtils;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityBlurBinding;

@Route(path = "/main/blur", name = "高斯模糊测试")
public class BlurActivity extends BaseActivity<ActivityBlurBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_blur;
    }

    @Override
    public void initView() {
        ThreadExecutor.get().executorService.execute(() -> {
            BitmapDrawable draw = (BitmapDrawable) XResUtils.getDrawable(R.mipmap.icon_bubble);
            Bitmap outPutBitmap = Bitmap.createBitmap(draw.getBitmap().getWidth(), draw.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);

            RenderScript renderScript = RenderScript.create(APP.get());

            ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            Allocation inAllocation = Allocation.createFromBitmap(renderScript, draw.getBitmap());
            Allocation outAllocation = Allocation.createFromBitmap(renderScript, outPutBitmap);

            //radius取值（0,25]，值越大，模糊效果越明显
            scriptIntrinsicBlur.setRadius(25f);

            //设置输入的Allocation
            scriptIntrinsicBlur.setInput(inAllocation);
            //渲染输入的效果，然后赋值给outAllocation
            scriptIntrinsicBlur.forEach(outAllocation);

            outAllocation.copyTo(outPutBitmap);

            ThreadExecutor.runOnUiThread(() -> {
                mViewBinding.iv.setImageBitmap(outPutBitmap);
            });
        });
    }

    @Override
    public void initData() {

    }
}
