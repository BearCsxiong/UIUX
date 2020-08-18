package me.csxiong.uiux.config;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;

import java.util.Map;

import me.csxiong.library.base.IAppDelegate;
import me.csxiong.uiux.BuildConfig;
import me.csxiong.uiux.ui.dataMask.MaskContainer;
import me.csxiong.uiux.ui.dataMask.MaskType;
import me.csxiong.uiux.ui.dataMask.mask.BaseMask;
import me.csxiong.uiux.ui.dataMask.mask.EmptyMask;
import me.csxiong.uiux.ui.dataMask.mask.ErrorMask;
import me.csxiong.uiux.ui.dataMask.mask.LoadingMask;
import me.csxiong.uiux.ui.http.HttpLogger;
import me.csxiong.uiux.ui.http.Preconditions;
import me.csxiong.uiux.ui.http.XHttp;
import me.csxiong.uiux.ui.widget.SimpleFooter;
import okhttp3.logging.HttpLoggingInterceptor;

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

        Logger.addLogAdapter(new AndroidLogAdapter());

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);

        /**
         * mask代理
         */
        MaskContainer.setMasksDelegate(new MaskContainer.MasksDelegate() {
            @Override
            public void onCreateMasks(Map<String, BaseMask> masks) {
                masks.put(MaskType.EMPTY, new EmptyMask());
                masks.put(MaskType.ERROR, new ErrorMask());
                masks.put(MaskType.LOADING, new LoadingMask());
            }
        });

        XHttp.init(new XHttp.Config()
                .addInterceptors(new HttpLoggingInterceptor(new HttpLogger(Preconditions.TAG)))
                .apply());

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new SimpleFooter(context);
            }
        });

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
