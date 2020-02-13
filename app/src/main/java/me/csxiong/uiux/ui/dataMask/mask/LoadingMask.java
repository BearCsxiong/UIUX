package me.csxiong.uiux.ui.dataMask.mask;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import me.csxiong.library.widget.XLoadingView;
import me.csxiong.uiux.R;
import me.csxiong.uiux.ui.dataMask.DataMaskHelper;
import me.csxiong.uiux.ui.dataMask.MaskType;

/**
 * @Desc : 加载Mask
 * @Author : csxiong - 2020-02-13
 */
public class LoadingMask extends BaseMask {

    @Override
    public int getLayoutId() {
        return R.layout.mask_loading;
    }

    @Override
    public String getTag() {
        return MaskType.LOADING;
    }

}
