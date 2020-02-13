package me.csxiong.uiux.ui.dataMask.mask;

import me.csxiong.uiux.R;
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
