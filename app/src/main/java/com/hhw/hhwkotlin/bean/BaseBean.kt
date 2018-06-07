package com.hhw.hhwkotlin.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joe.
 */
@SuppressLint("ParcelCreator")// 用于处理 Lint 的错误提示
@Parcelize
open class BaseBean : Parcelable {
}