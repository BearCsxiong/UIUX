package me.csxiong.uiux.ui.dataMask.mask;

import me.csxiong.uiux.R;
import me.csxiong.uiux.ui.dataMask.MaskType;

/**
 * @Desc : 空Mask
 * @Author : csxiong - 2020-02-13
 */
public class EmptyMask extends BaseMask {

    @Override
    public int getLayoutId() {
        return R.layout.mask_empty;
    }

    @Override
    public String getTag() {
        return MaskType.EMPTY;
    }
}
