package com.hhw.hhwkotlin.ui.view.xlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.hhw.hhwkotlin.R

/**
 * Created by Joe.
 */
class XListViewFooter : LinearLayout {

    companion object {
        val STATE_NORMAL: Int = 0
        val STATE_READY: Int = 1
        val STATE_REFRESHING: Int = 2
    }
    private lateinit var mContext: Context
    private lateinit var mContentView: View


    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    fun initView(context: Context) {
        mContext = context
        mContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.xlistview_footer, null)
        addView(mContentView)
        mContentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun setBottomMargin(height: Int) {
        var mHeight = height
        if (mHeight < 0) mHeight = 0
        var lp = mContentView.layoutParams as LinearLayout.LayoutParams
        lp.bottomMargin = mHeight
        mContentView.layoutParams = lp
    }

    fun getBottomMargin(): Int {
        var lp = mContentView.layoutParams as LinearLayout.LayoutParams
        return lp.bottomMargin
    }

    fun hide() {
        var lp = mContentView.layoutParams as LinearLayout.LayoutParams
        lp.height = 0
        mContentView.layoutParams = lp
    }

    fun show() {
        var lp = mContentView.layoutParams as LinearLayout.LayoutParams
        lp.height = LayoutParams.WRAP_CONTENT
        mContentView.layoutParams = lp
    }

}