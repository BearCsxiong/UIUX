package me.csxiong.camera.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.csxiong.library.widget.GestureImageView;

public abstract class ItemPagerImageBinding extends ViewDataBinding {
  @NonNull
  public final GestureImageView iv;

  protected ItemPagerImageBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, GestureImageView iv) {
    super(_bindingComponent, _root, _localFieldCount);
    this.iv = iv;
  }

  @NonNull
  public static ItemPagerImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemPagerImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemPagerImageBinding>inflate(inflater, me.csxiong.camera.R.layout.item_pager_image, root, attachToRoot, component);
  }

  @NonNull
  public static ItemPagerImageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemPagerImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemPagerImageBinding>inflate(inflater, me.csxiong.camera.R.layout.item_pager_image, null, false, component);
  }

  public static ItemPagerImageBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemPagerImageBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemPagerImageBinding)bind(component, view, me.csxiong.camera.R.layout.item_pager_image);
  }
}
