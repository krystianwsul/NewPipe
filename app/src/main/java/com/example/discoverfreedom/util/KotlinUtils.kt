package com.example.discoverfreedom.util

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver


fun View.addOneShotGlobalLayoutListener(action: () -> Unit) = viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)

        action()
    }
})

fun Context.pxToDp(px: Int): Float {
    val density = resources.displayMetrics.density
    return px / density
}