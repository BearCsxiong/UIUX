package me.csxiong.uiux.ui

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import me.csxiong.library.utils.XToast

/**
 * @Desc   : 由dagger提供注入的ViewModel
 * @Author : csxiong create on 2019/8/22
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("CheckResult")
    fun showLoading(view: View?) {
        XToast.show("ShowLoading")
    }

}