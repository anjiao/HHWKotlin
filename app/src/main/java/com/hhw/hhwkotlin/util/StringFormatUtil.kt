package com.hhw.hhwkotlin.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Joe.
 */
object StringFormatUtil {

    val DATE_FORMAT_DAY: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    public fun getNowTime(): String {
        return DATE_FORMAT_DAY.format(Date())
    }

}