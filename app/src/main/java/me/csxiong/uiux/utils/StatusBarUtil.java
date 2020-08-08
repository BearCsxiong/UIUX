package me.csxiong.uiux.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.csxiong.library.base.APP;
import me.csxiong.library.utils.XDisplayUtil;
import me.csxiong.library.utils.XResUtils;

/**
 * Created by wangxi on 2018/3/7.
 * 对全面屏刘海屏适配，状态栏沉浸式，状态栏透明
 */

public class StatusBarUtil {
    public static final String TAG = "StatusBarUtil";
    public static final int ADJUST_PADDING_TOP = 0;
    public static final int ADJUST_MARGIN_TOP = 1;
    public static final int ADJUST_HEIGHT = 2;
    public static final int ADJUST_NONE = 3;

    public static final int NONE_ADJUST = 0;
    public static final int NEED_ADJUST = 1;
    public static final int HAS_ADJUSTED = 2;

    public static final String ADJUSTED_SUFFIX = "_adjusted";
    private static StringBuilder sb = new StringBuilder();
    private static int statusBarHeight = 0;
    private static boolean enable = true;
    private static List<String> sTranslateStatusList = new ArrayList<>();
    private static Map<String, Boolean> sActivityFullScreenMap = new HashMap<>(16);

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = context.getResources().getDimensionPixelOffset(resourceId);
                }
                if (statusBarHeight == 0) {
                    statusBarHeight = XDisplayUtil.dpToPxInt(25);
                }
            }
        }
        // Log.e(TAG, "getStatusBarHeight=" + statusBarHeight);
        return statusBarHeight;
    }

    public static String getString(int id) {
        return XResUtils.getString(id);
    }

    private static int getViewAdjustStatus(View view) {
        if (isAdjustView(view) != ADJUST_NONE) {
            String tag = (String) view.getTag();
            if (tag.endsWith(ADJUSTED_SUFFIX)) {
                return HAS_ADJUSTED;
            } else if (enableTranslucentStatus()) {
                return NEED_ADJUST;
            }
        }
        return NONE_ADJUST;
    }

    private static String getViewTag(View view) {
        if (view != null && view.getTag() != null && view.getTag() instanceof String) {
            return (String) view.getTag();
        }
        return null;
    }

    private static int isAdjustView(View view) {
        // Log.e("tag", "isAdjustView" + view.getTag().toString());
        return ADJUST_NONE;
    }

    private static View findAdjustStatusView(View contentView, int index, int tagId, boolean enableMultiView) {
        sb.setLength(0);
        sb.append(getString(tagId));
        if (enableMultiView) {
            sb.append("_").append(index);
        }
        String tag = sb.toString();
        if (contentView instanceof ViewGroup) {
            ViewGroup rootView = (ViewGroup) contentView;
            View adjustView = rootView.findViewWithTag(tag);
            if (adjustView != null) {
                return adjustView;
            }
            adjustView = rootView.findViewWithTag(tag + ADJUSTED_SUFFIX);
            if (adjustView != null) {
                return adjustView;
            }
        } else if (isAdjustView(contentView) != ADJUST_NONE) {
            return contentView;
        }
        return null;
    }

    // 调整过，给tag增加ADJUST_SUFFIX进行标志，还原时去掉
    private static void addOrDeleteAdjustSuffix(View view, boolean add, int viewAdjustStatus) {
        String tag = getViewTag(view);
        if (!TextUtils.isEmpty(tag) && view != null) {
            if (add && viewAdjustStatus == NEED_ADJUST && !tag.endsWith(ADJUSTED_SUFFIX)) {
                tag = tag + ADJUSTED_SUFFIX;
            } else if (!add && viewAdjustStatus == HAS_ADJUSTED) {
                tag = tag.replace(ADJUSTED_SUFFIX, "");
            }
            view.setTag(tag);
        }
    }

    public static void adjustStatusView(View adjustView) {
        int adjustHeight = getStatusBarHeight(APP.get());
        if (adjustView != null) {
            ViewGroup.LayoutParams layoutParams = adjustView.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParam = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParam.setMargins(marginLayoutParam.leftMargin, marginLayoutParam.topMargin + adjustHeight,
                    marginLayoutParam.rightMargin, marginLayoutParam.bottomMargin);
            }
        }
    }

    // 默认刘海屏属于全面屏，所有全面屏都做状态栏透明处理
    public static boolean enableTranslucentStatus() {
        return ViewUtils.isFullScreenDevice() && enable;
    }

    // 只有在全面屏，刘海屏时才启动透明状态栏
    public static void myStatusBar(Activity activity, boolean translucentStatus) {
        myStatusBar(activity, translucentStatus, Color.WHITE);
    }

    public static void myStatusBar(Dialog dialog, boolean translucentStatus) {
        Window window = dialog.getWindow();
        if (enableTranslucentStatus()) {
            if (translucentStatus) {
                myStatusBar(window, Color.WHITE);
            }
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static void myStatusBar(Activity activity, boolean translucentStatus, int color) {
        sActivityFullScreenMap.put(activity.getComponentName().getClassName(), ViewUtils.isFullScreenDevice());
        if (enableTranslucentStatus()) {
            if (translucentStatus) {
                myStatusBar(activity.getWindow(), color);
            }
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    // 设置状态栏全透明
    public static void setTranslateBar(Activity activity, boolean translucentStatus) {
        if (enableTranslucentStatus() && translucentStatus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 这样设置状态栏和导航栏都透明了
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.BLACK);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true, activity.getWindow());
            }
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Window win) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    // /白色可以替换成其他浅色系
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void myStatusBar(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(window, true)) {// MIUI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 5.0
                    window.setStatusBarColor(color);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else if (FlymeSetStatusBarLightMode(window, true)) {// Flyme
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 5.0
                    window.setStatusBarColor(color);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4
                    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(color);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            setTranslucentStatus(true, window);
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);// 状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);// 清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static void setAdapterFullScreen(Window window) {
        if (enableTranslucentStatus()) {
            setNotFullScreen(window);
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void setNotFullScreen(Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // 判断activity是否从全面屏到非全屏变化
    public static boolean isActivityScreenChange(Activity activity) {
        String key = activity == null ? "" : activity.getComponentName().getClassName();
        boolean isFullScreen = ViewUtils.isFullScreenDevice();
        if (sActivityFullScreenMap.containsKey(key)) {
            boolean result = sActivityFullScreenMap.get(key);
            if (isFullScreen != result) {
                sActivityFullScreenMap.put(key, isFullScreen);
                return true;
            }
        } else {
            sActivityFullScreenMap.put(key, isFullScreen);
        }
        return false;
    }

    public static void setSystemUI(Activity activity) {
        if (activity != null && activity.getWindow() != null) {
            setAdapterFullScreen(activity.getWindow());
            if (enableTranslucentStatus()) {
                if (isActivityUseTranslateStatus(activity)) {
                    setTranslucentStatus(true, activity.getWindow());
                    int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_VISIBLE;
                    activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
                } else {
                    setTranslucentStatus(false, activity.getWindow());
                    int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_VISIBLE;
                    activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
                }
            }
        }
    }

    public static boolean isActivityUseTranslateStatus(Activity activity) {
        if (activity != null && sTranslateStatusList.contains(activity.getComponentName().getClassName())) {
            return true;
        }
        return false;
    }

    public static void addActivityTranslateStatusList(Activity activity) {
        if (activity != null && !sTranslateStatusList.contains(activity.getComponentName().getClassName())) {
            sTranslateStatusList.add(activity.getComponentName().getClassName());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void useImmersiveMode(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        StatusBarUtil.addActivityTranslateStatusList(activity);

        Window window = activity.getWindow();
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (!StatusBarUtil.enableTranslucentStatus()) { // 如果是刘海屏，全面屏，使用透明状态栏
            option = option | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        window.getDecorView().setSystemUiVisibility(option);

    }
}
