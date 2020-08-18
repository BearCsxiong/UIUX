package me.csxiong.uiux.ui.gradient;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.csxiong.library.base.BaseActivity;
import me.csxiong.library.utils.XAnimator;
import me.csxiong.library.utils.XAnimatorCalculateValuer;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.uiux.R;
import me.csxiong.uiux.databinding.ActivityGradientBinding;
import me.csxiong.uiux.utils.ViewUtils;

@Route(path = "/main/gradient", name = "渐变顶部栏")
public class GradientViewAcitivity extends BaseActivity<ActivityGradientBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_gradient;
    }

    @Override
    public void initView() {
        mViewBinding.gv.start();
        mViewBinding.gvCenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding.gv.stop();
        mViewBinding.gvCenter.stop();
    }

    private XAnimatorCalculateValuer totalWidthValuer = new XAnimatorCalculateValuer();
    private XAnimatorCalculateValuer contentWidthValuer = new XAnimatorCalculateValuer();
    private XAnimatorCalculateValuer translateXValuer = new XAnimatorCalculateValuer();

    @Override
    public void initData() {

    }

    boolean isExpand;

    private XAnimator xAnimator = XAnimator.ofFloat(0, 1f)
            .duration(300)
            .setAnimationListener(new XAnimator.XAnimationListener() {
                @Override
                public void onAnimationUpdate(float fraction, float value) {
                    ViewUtils.setWidth(mViewBinding.cv, (int) totalWidthValuer.caculateValue(fraction));
                    ViewUtils.setWidth(mViewBinding.tv, (int) contentWidthValuer.caculateValue(fraction));
                    mViewBinding.cv.setTranslationX(translateXValuer.caculateValue(fraction));
                }

                @Override
                public void onAnimationStart(XAnimator animation) {
                    if (isExpand) {
                        totalWidthValuer.mark(mViewBinding.cv.getWidth(), XDisplayUtil.dpToPx(120));
                        contentWidthValuer.mark(mViewBinding.tv.getWidth(), XDisplayUtil.dpToPx(90));
                        translateXValuer.mark(mViewBinding.cv.getTranslationX(), -XDisplayUtil.dpToPx(20));
                    } else {
                        totalWidthValuer.mark(mViewBinding.cv.getWidth(), XDisplayUtil.dpToPx(30));
                        contentWidthValuer.mark(mViewBinding.tv.getWidth(), 0);
                        translateXValuer.mark(mViewBinding.cv.getTranslationX(), 0);
                    }
                    mViewBinding.gvCenter.stop();
                }

                @Override
                public void onAnimationEnd(XAnimator animation) {
                    mViewBinding.gvCenter.start();
                }

                @Override
                public void onAnimationCancel(XAnimator animation) {

                }
            });

    public void onExpand(View v) {
        if (isExpand) {
            return;
        }
        isExpand = true;
        xAnimator.cancel();
        xAnimator.start();
    }

    public void onShrink(View v) {
        if (!isExpand) {
            return;
        }
        isExpand = false;
        xAnimator.cancel();
        xAnimator.start();
    }
}
