package com.hhw.hhwkotlin

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hhw.hhwkotlin.adapter.MainPagerAdapter
import com.hhw.hhwkotlin.ui.base.BaseActivity
import com.hhw.hhwkotlin.ui.base.BaseFrag
import com.hhw.hhwkotlin.ui.fragment.HomeFrag
import com.hhw.hhwkotlin.ui.fragment.MeFrag
import com.hhw.hhwkotlin.ui.fragment.StoreFrag
import com.hhw.hhwkotlin.ui.view.HackyViewPager

class MainActivity : BaseActivity() {


    private lateinit var vp: HackyViewPager
    private var frags: MutableList<BaseFrag> = mutableListOf()
    private lateinit var mainPagerAdapter: MainPagerAdapter

    private lateinit var tvHome: TextView
    private lateinit var tvStore: TextView
    private lateinit var tvMe: TextView
    private lateinit var ivHome: ImageView
    private lateinit var ivStore: ImageView
    private lateinit var ivMe: ImageView
    private lateinit var llHome: LinearLayout
    private lateinit var llStore: LinearLayout
    private lateinit var llMe: LinearLayout
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vp = findViewById(R.id.vp)
        tvHome = findViewById(R.id.tvHome)
        tvStore = findViewById(R.id.tvStore)
        tvMe = findViewById(R.id.tvMe)
        ivHome = findViewById(R.id.ivHome)
        ivStore = findViewById(R.id.ivStore)
        ivMe = findViewById(R.id.ivMe)
        llHome = findViewById(R.id.llHome)
        llStore = findViewById(R.id.llStore)
        llMe = findViewById(R.id.llMe)
        llHome.setOnClickListener {
            currentIndex = 0
            initPageStatus(currentIndex)
        }
        llStore.setOnClickListener {
            currentIndex = 1
            initPageStatus(currentIndex)
        }
        llMe.setOnClickListener {
            currentIndex = 2
            initPageStatus(currentIndex)
        }
        initPage()
    }

    private fun initPageStatus(index: Int) {
        when (currentIndex) {
            0 -> {
                tvHome.setTextColor(resources.getColor(R.color.main_green))
                tvStore.setTextColor(resources.getColor(R.color.bottom_black))
                tvMe.setTextColor(resources.getColor(R.color.bottom_black))
                ivHome.setImageResource(R.mipmap.home_sel)
                ivStore.setImageResource(R.mipmap.store)
                ivMe.setImageResource(R.mipmap.me)
            }
            1 -> {
                tvHome.setTextColor(resources.getColor(R.color.bottom_black))
                tvStore.setTextColor(resources.getColor(R.color.main_green))
                tvMe.setTextColor(resources.getColor(R.color.bottom_black))
                ivHome.setImageResource(R.mipmap.home)
                ivStore.setImageResource(R.mipmap.store_sel)
                ivMe.setImageResource(R.mipmap.me)
            }
            else -> {
                tvHome.setTextColor(resources.getColor(R.color.bottom_black))
                tvStore.setTextColor(resources.getColor(R.color.bottom_black))
                tvMe.setTextColor(resources.getColor(R.color.main_green))
                ivHome.setImageResource(R.mipmap.home)
                ivStore.setImageResource(R.mipmap.store)
                ivMe.setImageResource(R.mipmap.me_sel)
            }
        }
        vp.currentItem = currentIndex
    }

    private fun initPage() {
        frags.clear()
        frags.add(HomeFrag())
        frags.add(StoreFrag())
        frags.add(MeFrag())
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager, frags)
        vp.adapter = mainPagerAdapter
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
            }

        })
        vp.currentItem = 0
        vp.isLocked = false
    }
}
