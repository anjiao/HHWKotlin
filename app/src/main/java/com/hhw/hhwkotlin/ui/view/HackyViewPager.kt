package com.hhw.hhwkotlin.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Joe.
 */
class HackyViewPager : ViewPager {
    var isLocked: Boolean = false

    constructor(mContext: Context) : super(mContext) {
        this.isLocked = false
    }

    constructor(mContext: Context, mAttributeSet: AttributeSet) : super(mContext, mAttributeSet) {
        this.isLocked = false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev)
            } catch (e: IllegalArgumentException) {
            }
            return false
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !isLocked && super.onTouchEvent(ev)
    }
}