package me.csxiong.camera.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class ItemAlbumBinding extends ViewDataBinding {
  @NonNull
  public final ImageView iv;

  protected ItemAlbumBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ImageView iv) {
    super(_bindingComponent, _root, _localFieldCount);
    this.iv = iv;
  }

  @NonNull
  public static ItemAlbumBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemAlbumBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemAlbumBinding>inflate(inflater, me.csxiong.camera.R.layout.item_album, root, attachToRoot, component);
  }

  @NonNull
  public static ItemAlbumBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemAlbumBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemAlbumBinding>inflate(inflater, me.csxiong.camera.R.layout.item_album, null, false, component);
  }

  public static ItemAlbumBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemAlbumBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemAlbumBinding)bind(component, view, me.csxiong.camera.R.layout.item_album);
  }
}
