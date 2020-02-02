package me.csxiong.camera;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.csxiong.camera.databinding.ActivityAlbumBindingImpl;
import me.csxiong.camera.databinding.ActivityPhotoBindingImpl;
import me.csxiong.camera.databinding.ItemAlbumBindingImpl;
import me.csxiong.camera.databinding.ItemPagerImageBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYALBUM = 1;

  private static final int LAYOUT_ACTIVITYPHOTO = 2;

  private static final int LAYOUT_ITEMALBUM = 3;

  private static final int LAYOUT_ITEMPAGERIMAGE = 4;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(4);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.csxiong.camera.R.layout.activity_album, LAYOUT_ACTIVITYALBUM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.csxiong.camera.R.layout.activity_photo, LAYOUT_ACTIVITYPHOTO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.csxiong.camera.R.layout.item_album, LAYOUT_ITEMALBUM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.csxiong.camera.R.layout.item_pager_image, LAYOUT_ITEMPAGERIMAGE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYALBUM: {
          if ("layout/activity_album_0".equals(tag)) {
            return new ActivityAlbumBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_album is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPHOTO: {
          if ("layout/activity_photo_0".equals(tag)) {
            return new ActivityPhotoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_photo is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMALBUM: {
          if ("layout/item_album_0".equals(tag)) {
            return new ItemAlbumBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_album is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMPAGERIMAGE: {
          if ("layout/item_pager_image_0".equals(tag)) {
            return new ItemPagerImageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_pager_image is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(2);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new me.csxiong.library.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(4);

    static {
      sKeys.put("layout/activity_album_0", me.csxiong.camera.R.layout.activity_album);
      sKeys.put("layout/activity_photo_0", me.csxiong.camera.R.layout.activity_photo);
      sKeys.put("layout/item_album_0", me.csxiong.camera.R.layout.item_album);
      sKeys.put("layout/item_pager_image_0", me.csxiong.camera.R.layout.item_pager_image);
    }
  }
}
