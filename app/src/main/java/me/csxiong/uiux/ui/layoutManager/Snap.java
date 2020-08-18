package me.csxiong.uiux.ui.layoutManager;


import androidx.annotation.IntDef;

@IntDef({Snap.LEFT, Snap.RIGHT, Snap.CENTER})
public @interface Snap {
    int LEFT = 0;
    int RIGHT = 1;
    int CENTER = 2;
}