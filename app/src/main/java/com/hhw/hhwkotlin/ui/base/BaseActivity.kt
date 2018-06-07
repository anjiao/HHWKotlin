package com.hhw.hhwkotlin.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hhw.hhwkotlin.MainApp

/**
 * Created by Joe.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this
    }

    fun showToast(msg: String) {
        Toast.makeText(MainApp.myApp, msg, Toast.LENGTH_LONG).show()
    }
}