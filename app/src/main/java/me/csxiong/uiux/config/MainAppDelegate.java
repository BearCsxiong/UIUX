package me.csxiong.uiux.config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

import me.csxiong.library.base.APP;
import me.csxiong.library.base.IAppDelegate;
import me.csxiong.uiux.BuildConfig;
import me.csxiong.uiux.di.DaggerUIComponent;

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
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
