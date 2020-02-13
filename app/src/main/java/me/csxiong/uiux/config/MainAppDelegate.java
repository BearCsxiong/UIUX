package me.csxiong.uiux.config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Map;

import me.csxiong.library.base.APP;
import me.csxiong.library.base.IAppDelegate;
import me.csxiong.uiux.BuildConfig;
import me.csxiong.uiux.di.DaggerUIComponent;
import me.csxiong.uiux.ui.dataMask.DataMask;
import me.csxiong.uiux.ui.dataMask.MaskType;
import me.csxiong.uiux.ui.dataMask.mask.BaseMask;
import me.csxiong.uiux.ui.dataMask.mask.EmptyMask;
import me.csxiong.uiux.ui.dataMask.mask.ErrorMask;
import me.csxiong.uiux.ui.dataMask.mask.LoadingMask;

/**
 * @Desc : 主App代理
 * @Author : csxiong - 2020-02-02
 */
public class MainAppDelegate implements IAppDelegate {

    @Override
    public void attachBaseContext(@NonNull Context context) {
        MultiDex.install(context);
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);

        /**
         * 所有UI组件反注入到APP中的Injector中
         */
        DaggerUIComponent.builder()
                .appComponent(APP.get().getAppComponent())
                .build()
                .inject((APP) application);

        /**
         * mask代理
         */
        DataMask.setMasksDelegate(new DataMask.MasksDelegate() {
            @Override
            public void onCreateMasks(Map<String, BaseMask> masks) {
                masks.put(MaskType.EMPTY, new EmptyMask());
                masks.put(MaskType.ERROR, new ErrorMask());
                masks.put(MaskType.LOADING, new LoadingMask());
            }
        });
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
