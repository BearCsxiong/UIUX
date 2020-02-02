package me.csxiong.camera.di;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.csxiong.camera.ui.album.AlbumActivity;
import me.csxiong.library.di.scope.ActivityScope;

@Module(subcomponents = CameraActivityModule_InjectAlbum.AlbumActivitySubcomponent.class)
public abstract class CameraActivityModule_InjectAlbum {
  private CameraActivityModule_InjectAlbum() {}

  @Binds
  @IntoMap
  @ActivityKey(AlbumActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      AlbumActivitySubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AlbumActivitySubcomponent extends AndroidInjector<AlbumActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AlbumActivity> {}
  }
}
