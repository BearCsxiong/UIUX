package me.csxiong.uiux.ui

import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    fun main() {

        viewModelScope.launch {

            flow {
                (1..5).forEach {
                    delay(1000)
                    emit(it)
                }
            }.flowOn(Dispatchers.IO)
                    .catch {

                    }
                    .onStart {
                        XToast.info("开始")
                    }
                    .onCompletion {
                        XToast.success("完成")
                    }
                    .collect {
                        XToast.warning(it.toString())
                    }
        }

    }

}