package com.hhw.hhwkotlin.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hhw.hhwkotlin.MainApp
import com.hhw.hhwkotlin.R
import com.hhw.hhwkotlin.bean.HomeBean
import com.hhw.hhwkotlin.ui.base.BaseFrag
import com.hhw.hhwkotlin.ui.view.xlist.XListView

/**
 * Created by Joe.
 */
class HomeFrag : BaseFrag() {


    var homeBeans: MutableList<HomeBean> = mutableListOf()
    private lateinit var headerView: View
    private lateinit var xList: XListView
    private lateinit var homeAdapter: MyAdapter

    var handler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                100 -> {
                    xList.onRefreshComplete()
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.frag_home, container, false)
        xList = view.findViewById(R.id.xlist)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListHeader()
        homeBeans.add(HomeBean())
        homeBeans.add(HomeBean())
        homeBeans.add(HomeBean())
        homeBeans.add(HomeBean())
        homeAdapter = MyAdapter()
        xList.adapter = homeAdapter
        xList.isShowFoot(false)
        xList.mListViewListener = object : XListView.IXListViewListener {
            override fun onLastItemVisible() {
            }

            override fun onRefresh() {
                handler.sendEmptyMessageDelayed(100, 2000)
            }

        }

    }

    private fun initListHeader() {
        headerView = LayoutInflater.from(mActivity).inflate(R.layout.view_home_header, null)
        xList.addHeaderView(headerView)
    }

    inner class MyAdapter : BaseAdapter() {

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var convertView = LayoutInflater.from(MainApp.myApp).inflate(R.layout.item_home_row, null)
            return convertView
        }

        override fun getItem(p0: Int): Any {
            return homeBeans[p0]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return homeBeans.size
        }

    }
}