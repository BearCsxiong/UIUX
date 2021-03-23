package me.csxiong.uiux.ui.background

import android.app.Application
import android.graphics.Bitmap
import android.graphics.PorterDuff
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * @Desc : 基础的Viewmodel
 * @Author : meitu - 3/18/21
 */
class BackgroundViewModel(application: Application) : AndroidViewModel(application) {

    val applyGradientType = MutableLiveData<BackgroundType>()

    val applyBackgroundType = MutableLiveData<Bitmap>()

    val applyMode = MutableLiveData<PorterDuff.Mode>()
}