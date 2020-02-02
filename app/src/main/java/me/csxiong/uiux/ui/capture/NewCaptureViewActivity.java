package me.csxiong.uiux.ui.capture;

import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.VibratorUtils;
import me.csxiong.library.utils.XToast;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityNewCaptureBinding;

@Route(path = "/main/new/capture", name = "新多人拍照手势控件")
public class NewCaptureViewActivity extends BaseActivity<ActivityNewCaptureBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_capture;
    }

    @Override
    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mViewBinding.cv.setOnProgressChangeListener(new NewCaptureView.OnProgressChangeListener() {

            @Override
            public void onProgressChange(int lastProgress, int progress, boolean isFromUser, int state) {
                VibratorUtils.onShot(4);
            }

        });

        mViewBinding.cv.setOnCaptureTouchListener(() -> XToast.show("Capture"));
    }

    @Override
    public void initData() {

    }
}
