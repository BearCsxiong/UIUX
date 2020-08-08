package me.csxiong.uiux.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.library.utils.XResUtils;
import me.csxiong.uiux.R;


/**
 * Created by csxiong on 2018/10/13.
 */

public class SimpleFooter extends RelativeLayout implements RefreshFooter {
    public SimpleFooter(Context context) {
        this(context, null);
    }

    public SimpleFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    RelativeLayout relativeLayout;
    TextView tv;
    ProgressBar pb;
    ImageView iv;

    @SuppressLint("WrongConstant")
    private void initView() {
        relativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, XDisplayUtil.dpToPxInt(getContext().getResources().getDimension(R.dimen.x30)));
        relativeLayout.setLayoutParams(lp);

        tv = new TextView(getContext());
        tv.setId(R.id.load_more);
        LayoutParams tlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        tv.setTextColor(0xffffffff);
        tv.setLayoutParams(tlp);

        pb = new ProgressBar(getContext());
        pb.setId(R.id.load_more_progress);
        LayoutParams pblp = new LayoutParams(XResUtils.getDimenPx(R.dimen.x10), XResUtils.getDimenPx(R.dimen.x10));
        pb.setScrollBarStyle(android.R.style.Widget_DeviceDefault_Light_ProgressBar_Small_Inverse);
        pblp.addRule(RelativeLayout.LEFT_OF, R.id.load_more);
        pblp.addRule(RelativeLayout.CENTER_VERTICAL, -1);
        pblp.setMargins(0, 0, XResUtils.getDimenPx(R.dimen.x8), 0);
        pb.setLayoutParams(pblp);

        iv = new ImageView(getContext());
        pb.setId(R.id.load_more_success);
        LayoutParams ilp = new LayoutParams(XResUtils.getDimenPx(R.dimen.x15), XResUtils.getDimenPx(R.dimen.x15));
        ilp.addRule(RelativeLayout.LEFT_OF, R.id.load_more);
        ilp.addRule(RelativeLayout.CENTER_VERTICAL, -1);
        ilp.setMargins(0, 0, XResUtils.getDimenPx(R.dimen.x8), 0);
        iv.setImageResource(R.mipmap.qq_refresh_success);
        iv.setLayoutParams(ilp);
        iv.setVisibility(GONE);

        relativeLayout.addView(tv);
        relativeLayout.addView(pb);
        relativeLayout.addView(iv);
        addView(relativeLayout);
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        pb.setVisibility(VISIBLE);
//        iv.setVisibility(GONE);
        tv.setText("加载中...");
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        iv.setVisibility(VISIBLE);
        pb.setVisibility(GONE);
//        tv.setText(XResUtils.getString(success ? R.string.loading_success : R.string.loading_fail));
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }
}
