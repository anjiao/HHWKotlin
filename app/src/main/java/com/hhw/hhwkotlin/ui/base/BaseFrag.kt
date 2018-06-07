package com.hhw.hhwkotlin.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Joe.
 */
open class BaseFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.activity!!
        mActivity = this.activity!!
    }

}