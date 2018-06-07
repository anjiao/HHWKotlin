package com.hhw.hhwkotlin

import android.app.Application
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hhw.hhwkotlin.util.CircleBitmapTarget

/**
 * Created by Joe.
 */
class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var myApp: MainApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }


    fun disPlayImage(image: ImageView, path: String) {
        GlideApp.with(this)
                .asBitmap()
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(image)
    }

    fun disPlayImage(image: ImageView, path: String, res: Int) {
        GlideApp.with(this)
                .asBitmap()
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(res)
                .placeholder(res)
                .into(image)
    }

    fun disPlayUserImage(image: ImageView, path: String, with: Int, heigh: Int) {
        var mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        mRequestOptions.override(with, heigh)
        GlideApp.with(this)
                .asBitmap()
                .load(path)
                .apply(mRequestOptions)
                .dontAnimate()
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(image)
    }

    fun disPlayUserImage(image: ImageView, path: String) {
        var mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        GlideApp.with(this)
                .asBitmap()
                .load(path)
                .apply(mRequestOptions)
                .dontAnimate()
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(image)
    }

}