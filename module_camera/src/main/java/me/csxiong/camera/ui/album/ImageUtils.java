package me.csxiong.camera.ui.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import me.csxiong.library.base.APP;

/**
 * @Desc : 图片工具
 * @Author : Bear - 2020/5/24
 */
public class ImageUtils {

    public static Bitmap getBlurBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap outPutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(APP.get());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation inAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation outAllocation = Allocation.createFromBitmap(renderScript, outPutBitmap);

        //radius取值（0,25]，值越大，模糊效果越明显
        scriptIntrinsicBlur.setRadius(25f);

        //设置输入的Allocation
        scriptIntrinsicBlur.setInput(inAllocation);
        //渲染输入的效果，然后赋值给outAllocation
        scriptIntrinsicBlur.forEach(outAllocation);
        outAllocation.copyTo(outPutBitmap);
        return outPutBitmap;
    }
}
