package com.hhw.hhwkotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hhw.hhwkotlin.ui.base.BaseFrag

/**
 * Created by Joe.
 */
class MainPagerAdapter : FragmentPagerAdapter {

    var fm: FragmentManager
    lateinit var listFrag: MutableList<BaseFrag>

    override fun getItem(position: Int): Fragment {
        return listFrag.get(position)

    }

    override fun getCount(): Int {
        return listFrag.size
    }

    constructor(fm: FragmentManager) : super(fm) {
        this.fm = fm
    }

    constructor(fm: FragmentManager, frags: MutableList<BaseFrag>) : super(fm) {
        this.fm = fm
        this.listFrag = frags
    }

}