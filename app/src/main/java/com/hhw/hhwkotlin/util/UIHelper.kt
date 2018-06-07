package com.joe.kotlin.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 *
 * 界面跳转工具类
 * Created by Joe.
 */
object UIHelper {

    fun jumpToActivity(activity: Activity, clazz: Class<Any>) {
        val detailIntent = Intent()
        detailIntent.setClass(activity, clazz)
        activity.startActivity(detailIntent)
    }

    fun jumpToActivity(activity: Activity, clazz: Class<Any>, bundle: Bundle) {
        val detailIntent = Intent()
        detailIntent.setClass(activity, clazz)
        detailIntent.putExtras(bundle)
        activity.startActivity(detailIntent)
    }

}