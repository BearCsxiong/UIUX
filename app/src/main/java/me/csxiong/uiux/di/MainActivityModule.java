package me.csxiong.uiux.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.csxiong.library.di.scope.ActivityScope;
import me.csxiong.uiux.ui.MainActivity;
import me.csxiong.uiux.ui.book.BookListActivity;

/**
 * @Desc : 模块内部Activity注册 注册需要注入的Activity
 * @Author : csxiong create on 2019/7/17
 */
@Module
public abstract class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract MainActivity inject();

    @ActivityScope
    @ContributesAndroidInjector
    abstract BookListActivity injectScrect();

}
