package com.joe.kotlin.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 *
 * 手机屏幕相关工具类
 * Created by Joe.
 */
object ScreenUtils {

    fun getScreenWidth(context: Context): IntArray {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return intArrayOf(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

    fun dip2Px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}