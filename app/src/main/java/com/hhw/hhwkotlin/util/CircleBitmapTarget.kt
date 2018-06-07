package com.hhw.hhwkotlin.util

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * Created by Joe.
 */
class CircleBitmapTarget : ImageViewTarget<Bitmap> {

    constructor(imageView: ImageView) : super(imageView) {

    }

    override fun setResource(resource: Bitmap?) {
        bindCircleBitmapToImageView(resource)
    }

    override fun setDrawable(drawable: Drawable?) {
        if (drawable is BitmapDrawable) {
            var bitmap1 = drawable.bitmap
            bindCircleBitmapToImageView(bitmap1)
        } else {
            view.setImageDrawable(drawable)
        }
    }

    fun bindCircleBitmapToImageView(bitmap: Bitmap?) {
        bitmap as Bitmap
        var bitmapDrawable = RoundedBitmapDrawableFactory.create(view.getContext().getResources(), bitmap)
        bitmapDrawable.isCircular = true
        view.setImageDrawable(bitmapDrawable)

    }
}