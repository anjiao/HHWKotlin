package com.hhw.hhwkotlin.ui.view.xlist

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.hhw.hhwkotlin.R
import com.hhw.hhwkotlin.util.StringFormatUtil
import com.joe.kotlin.util.ScreenUtils

/**
 * Created by Joe.
 */
class XListView : ListView, AbsListView.OnScrollListener {


    var SCROLLBACK_HEADER = 0
    var SCROLLBACK_FOOTER = 1
    var SCROLL_DURATION = 500
    var CODE_REFRESH = 1
    var CODE_LOADING_MORE = 2
    var mLastY: Float = -1f
    var OFFSET_RADIO: Float = 2.3f
    lateinit var mScroller: Scroller
    lateinit var mScrollListener: OnScrollListener
    lateinit var mListViewListener: IXListViewListener
    lateinit var mHeaderView: XListViewHeader
    lateinit var mFooterView: XListViewFooter
    lateinit var mHeaderViewContent: RelativeLayout
    lateinit var mHeaderTimeView: TextView
    var mHeaderViewHeight: Int = 0
    var mTotalItemCount: Int = 0
    var mScrollBack: Int = 0
    var mEnablePullRefresh: Boolean = true
    var mPullRefreshing: Boolean = false
    var mEnablePullLoad: Boolean = false
    var mPullLoading: Boolean = false
    var mIsFooterReady: Boolean = false
    var mLastItemVisible: Boolean = false

    constructor(context: Context) : super(context) {
        initWithContext(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initWithContext(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initWithContext(context)
    }

    private fun initWithContext(context: Context) {
        mScroller = Scroller(context, DecelerateInterpolator())
        setOnScrollListener(this)
        mHeaderView = XListViewHeader(context)
        mHeaderViewContent = mHeaderView.findViewById(R.id.xlistview_header_content)
        mHeaderTimeView = mHeaderView.findViewById(R.id.xlistview_header_time)
        addHeaderView(mHeaderView)
        mFooterView = XListViewFooter(context)
        mHeaderView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mHeaderViewHeight = mHeaderViewContent.height
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
    }

    override fun setAdapter(adapter: ListAdapter?) {
        if (!mIsFooterReady) {
            mIsFooterReady = true
            addFooterView(mFooterView)
            isShowFoot(false)
        }
        super.setAdapter(adapter)
    }

    fun setPullRefreshEnable(enable: Boolean) {
        mEnablePullRefresh = enable
        if (!mEnablePullRefresh) {
            mHeaderViewContent.visibility = View.INVISIBLE
        } else {
            mHeaderViewContent.visibility = View.VISIBLE
        }
    }

    public fun isShowFoot(enable: Boolean) {
        mEnablePullLoad = enable
        if (!mEnablePullLoad) {
            mFooterView.hide()
        } else {
            mPullLoading = false
            mFooterView.show()
        }
    }

    private var mHandler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                CODE_REFRESH -> {
                    if (mPullRefreshing) {
                        mPullRefreshing = false
                        mHeaderView.clearAnimation()
                        resetHeaderHeight()
                        setRefreshTime(StringFormatUtil.getNowTime())
                    }
                }
                CODE_LOADING_MORE -> {
                    if (mPullLoading) {
                        mPullLoading = false
                    }
                }

            }

        }
    }

    fun onLoadingMoreComplete() {
        Thread(
                Runnable {
                    kotlin.run {
                        try {
                            Thread.sleep(1000)
                            mHandler.sendEmptyMessage(CODE_LOADING_MORE)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
        ).start()
    }

    fun onRefreshComplete() {
        Thread(
                Runnable {
                    kotlin.run {
                        try {
                            Thread.sleep(1000)
                            mHandler.sendEmptyMessage(CODE_REFRESH)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
        ).start()
    }

    fun invokeOnScrolling() {
        if (mScrollListener is OnXScrollListener) {
            (mScrollListener as OnXScrollListener).onXScrolling(this)
        }
    }

    fun updateHeaderHeight(delta: Float) {
        mHeaderView.setVisiableHeight(delta.toInt() + mHeaderView.getVisiableHeight())
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY)
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL)
            }
        }
        setSelection(0)
    }


    private fun setRefreshTime(nowTime: String) {
        mHeaderTimeView.text = nowTime
    }

    private fun resetHeaderHeight() {
        var height: Int = mHeaderView.getVisiableHeight()
        if (height == 0)
            return
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return
        }
        var finalHeight = 0
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight
        }
        mScrollBack = SCROLLBACK_HEADER
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION)
        invalidate()
    }

    fun updateFooterHeight(delta: Float) {
        var height = mFooterView.getBottomMargin() + delta.toInt()
        mFooterView.setBottomMargin(height)
    }


    fun resetFooterHeight() {
        var bottomMargin = mFooterView.getBottomMargin()
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION)
            invalidate()
        }
    }

    fun startLoadMore() {
        if (mPullLoading) {
            return
        }
        mPullLoading = true
        if (mListViewListener != null) {
            mListViewListener.onLastItemVisible()
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (mLastY == -1f) {
            mLastY = ev!!.rawY
        }
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                var nowRawY = ev.rawY
                var deltaY: Float = nowRawY - mLastY
                mLastY = ev.rawY
                if (firstVisiblePosition == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    updateHeaderHeight(deltaY / OFFSET_RADIO)
                    invokeOnScrolling()
                } else if (lastVisiblePosition == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0) && firstVisiblePosition != 0 && !mEnablePullLoad) {
                    updateFooterHeight(-deltaY / OFFSET_RADIO)
                }
            }
            else -> {
                mLastY = -1f
                if (firstVisiblePosition == 0) {
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING)
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh()
                        }
                    }
                    resetHeaderHeight()
                } else if (lastVisiblePosition == mTotalItemCount - 1) {
                    resetFooterHeight()
                }
            }

        }
        return super.onTouchEvent(ev)

    }

    fun startRefreshing() {
        var visibleHeight = ScreenUtils.dip2Px(context, 60f).toFloat()
        updateHeaderHeight(visibleHeight)
        invokeOnScrolling()
        mPullRefreshing = true
        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.currY)
            } else {
                mFooterView.setBottomMargin(mScroller.currY)
            }
            postInvalidate()
            invokeOnScrolling()
        }
        super.computeScroll()
    }

    override fun setOnScrollListener(l: OnScrollListener?) {
        mScrollListener = l as OnScrollListener
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        mTotalItemCount = totalItemCount
        mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mEnablePullLoad && mLastItemVisible) {
            startLoadMore()
        }
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    open interface IXListViewListener {
        fun onRefresh()
        fun onLastItemVisible()
    }

    open interface OnXScrollListener : OnScrollListener {
        fun onXScrolling(view: View)
    }

}