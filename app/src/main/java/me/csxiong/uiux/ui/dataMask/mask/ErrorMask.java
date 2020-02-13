package me.csxiong.uiux.ui.dataMask.mask;

import me.csxiong.uiux.R;
import me.csxiong.uiux.ui.dataMask.MaskType;

/**
 * @Desc : 错误Mask
 * @Author : csxiong - 2020-02-13
 */
public class ErrorMask extends BaseMask {

    @Override
    public int getLayoutId() {
        return R.layout.mask_error;
    }

    @Override
    public String getTag() {
        return MaskType.ERROR;
    }
}
