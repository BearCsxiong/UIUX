package me.csxiong.uiux.ui.layoutManager;

import android.support.annotation.IntDef;

@IntDef({Snap.LEFT, Snap.RIGHT, Snap.CENTER})
public @interface Snap {
    int LEFT = 0;
    int RIGHT = 1;
    int CENTER = 2;
}