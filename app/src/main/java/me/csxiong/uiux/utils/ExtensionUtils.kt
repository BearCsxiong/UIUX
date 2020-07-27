package me.csxiong.uiux.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.SparseArray
import android.view.View

val View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.alphaShow(duration: Long) {
    this.animate().setListener(null).cancel()
    this.visible()
    this.animate().alpha(1f)
            .setDuration(duration)
            .start()
}

fun View.alphaDismiss(duration: Long) {
    this.animate().setListener(null).cancel()
    if (this.visibility == View.GONE) {
        return
    }
    this.visible()
    this.animate().alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    gone()
                }

            })
            .start()
}

fun String.print(tag: String? = null, debugLevel: DebugLevel = DebugLevel.INFO) {
    when (debugLevel) {
        DebugLevel.ERROR -> {
            Log.e(tag ?: "csx", this)
        }
        DebugLevel.WARNING -> {
            Log.w(tag ?: "csx", this)
        }
        DebugLevel.DEBUG -> {
            Log.d(tag ?: "csx", this)
        }
        DebugLevel.VERBOSE -> {
            Log.v(tag ?: "csx", this)
        }
        else -> {
            Log.i(tag ?: "csx", this)
        }
    }
}

fun View.setShapeBgColor(color: Int) {
    val background = this.background as GradientDrawable?
    background?.setColor(color)
}

/**
 * 字符串转比例
 */
fun String.toAspectRatio(): Float {
    val asString = this.split(":")
    return try {
        asString.takeIf { asString.size == 2 }.run {
            asString[0].toFloat() / asString[1].toFloat()
        }
    } catch (e: Exception) {
        1f
    }
}

fun String.toColor(): Int {
    return try {
        if (this[0] == '#') {
            Color.parseColor(this)
        } else {
            Color.parseColor("#$this")
        }
    } catch (e: Exception) {
        Color.WHITE
    }
}

fun String?.notnull(default: String = ""): String {
    if (this != null) {
        return this
    }
    return default
}

fun Int?.notnull(default: Int = 0): Int {
    if (this != null) {
        return this
    }
    return default
}


fun <T> SparseArray<T>.values(): ArrayList<T> {
    val result = ArrayList<T>()
    try {
        for (index in 0 until size()) {
            result.add(valueAt(index))
        }
    } catch (e: Exception) {
        result.clear()
    }
    return result

}

fun Boolean?.notnull(default: Boolean = false): Boolean {
    if (this != null) {
        return this
    }
    return default
}
