package me.csxiong.uiux.di;

import dagger.Component;
import me.csxiong.library.base.APP;
import me.csxiong.library.di.component.AppComponent;
import me.csxiong.library.di.scope.AppScope;

@AppScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                MainUIModule.class
        })
public interface UIComponent {

    void inject(APP app);
}
