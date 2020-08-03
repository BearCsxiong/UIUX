package me.csxiong.uiux.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.csxiong.library.di.scope.FragmentScope;
import me.csxiong.uiux.ui.studio.book.BookFragment;

/**
 * @Desc : Fragment注入
 * @Author : csxiong create on 2019/7/17
 */
@Module
public abstract class MainFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract BookFragment injectBook();
}
