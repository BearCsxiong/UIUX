package me.csxiong.uiux.config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.util.List;

import me.csxiong.library.base.GlobalConfig;
import me.csxiong.library.base.IAppDelegate;
import me.csxiong.library.di.module.ClientModule;
import me.csxiong.library.di.module.GlobalConfigModule;
import me.csxiong.library.integration.imageloader.GlideImageLoader;
import me.csxiong.library.integration.imageloader.IImageLoader;
import me.csxiong.library.integration.imageloader.ImageLoader;

/**
 * @Desc : Main全局配置
 * @Author : csxiong create on 2019/7/17
 */
public class MainGlobalConfig implements GlobalConfig {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.loggerConfiguration(new ClientModule.LoggerConfiguration() {
            @Override
            public void configLogger(Context context, PrettyFormatStrategy.Builder builder) {
                builder.build();
            }
        });
        builder.imageloaderConfiguration(new ClientModule.ImageLoaderConfiguration() {
            @Override
            public IImageLoader configImageLoader(Context context) {
                return new GlideImageLoader();
            }
        });
    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppDelegate> lifecycles) {
        lifecycles.add(new MainAppDelegate());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
