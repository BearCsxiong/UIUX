package me.csxiong.uiux.utils

import me.csxiong.library.base.APP

object AppConfig : SPConfig(APP.get(), "AppConfig") {


    fun saveBookNumber(bookNumber: Int) {
        putValue("BookNumber", bookNumber)
    }

    fun getBookNumber(): Int {
        return getInt("BookNumber", 13648)
    }
}