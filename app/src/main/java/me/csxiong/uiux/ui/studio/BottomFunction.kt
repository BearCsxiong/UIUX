package me.csxiong.uiux.ui.studio

import android.support.v4.app.Fragment
import me.csxiong.uiux.ui.studio.book.BookFragment
import me.csxiong.uiux.ui.studio.selection.SelectionFragment

/**
 * @Desc : 书本Function
 * @Author : Bear - 2020/8/3
 */
enum class BottomFunction(var tag: String, var functionName: String, var fgClass: Class<out Fragment>) {

    /**
     * 书本集合的界面
     */
    Book("Book", "Book", BookFragment::class.java),
    /**
     * 选集界面
     */
    Selection("Selection", "Selection", SelectionFragment::class.java);

}