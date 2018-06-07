package com.hhw.hhwkotlin.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by Joe.
 */
object PhoneUtil {

    fun hideInputMethod(activity: Activity) {
        val mInputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            mInputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}