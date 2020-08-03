package me.csxiong.uiux.ui.studio

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import me.csxiong.library.base.XViewModel
import javax.inject.Inject

class BookStudioViewModel @Inject constructor(application: Application) : XViewModel(application) {

    val bottomFunctionChangeEvent = MutableLiveData<BottomFunction>()

    fun show(function: BottomFunction?) {
        bottomFunctionChangeEvent.value = function
    }

    fun isShow(): Boolean {
        return bottomFunctionChangeEvent.value != null
    }

}