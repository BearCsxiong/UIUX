package me.csxiong.camera.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class ActivityPhotoBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivTransition;

  @NonNull
  public final ViewPager vp;

  protected ActivityPhotoBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ImageView ivTransition, ViewPager vp) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivTransition = ivTransition;
    this.vp = vp;
  }

  @NonNull
  public static ActivityPhotoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPhotoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPhotoBinding>inflate(inflater, me.csxiong.camera.R.layout.activity_photo, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPhotoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPhotoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPhotoBinding>inflate(inflater, me.csxiong.camera.R.layout.activity_photo, null, false, component);
  }

  public static ActivityPhotoBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityPhotoBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityPhotoBinding)bind(component, view, me.csxiong.camera.R.layout.activity_photo);
  }
}
