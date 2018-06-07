package com.hhw.hhwkotlin.ui.view.xlist

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hhw.hhwkotlin.R

/**
 * Created by Joe.
 */
class XListViewHeader : LinearLayout {

    private lateinit var mContainer: LinearLayout
    private lateinit var mArrowImageView: ProgressBar
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mHintTextView: TextView//暂时只用到这个文字显示，其他想用到的自行处理

    companion object {
        val STATE_NORMAL: Int = 0
        val STATE_READY: Int = 1
        val STATE_REFRESHING: Int = 2
    }

    private var mState = STATE_NORMAL


    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        var lp = LayoutParams(LayoutParams.MATCH_PARENT, 0)
        mContainer = LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null) as LinearLayout
        addView(mContainer, lp)
        gravity = Gravity.CENTER
        mHintTextView = findViewById(R.id.xlistview_header_hint_textview)
        mArrowImageView = findViewById(R.id.xlistview_header_arrow)
        mProgressBar = findViewById(R.id.xlistview_header_progressbar)
    }

    fun setState(state: Int) {
        if (state == mState) return
        if (state == STATE_REFRESHING) {//放手刷新
            mArrowImageView.visibility = View.GONE
            mProgressBar.visibility = View.GONE
        } else {
            mHintTextView.visibility = View.VISIBLE
            mArrowImageView.visibility = View.GONE
            mProgressBar.visibility = View.GONE
        }

        when (state) {
            STATE_NORMAL -> {
                //没处理根据需求处理
                if (mState == STATE_READY) {
                }
                if (mState == STATE_REFRESHING) {
                }
                mHintTextView.setText(R.string.xlistview_header_hint_normal)
            }
            STATE_READY -> {
                if (mState != STATE_READY) {
                    mHintTextView.text = "松开载入最新内容"
                }
            }
            STATE_REFRESHING -> {
                mArrowImageView.visibility = View.VISIBLE
                mHintTextView.setText(R.string.xlistview_header_hint_loading)
                mHintTextView.visibility = View.GONE
            }
        }
        mState = state
    }

    fun setVisiableHeight(height: Int) {
        var mHeight = height
        if (mHeight < 0) mHeight = 0
        var lp = mContainer.layoutParams
        lp.height = mHeight
        mContainer.layoutParams = lp
    }

    fun getVisiableHeight(): Int {
        return mContainer.height
    }

}