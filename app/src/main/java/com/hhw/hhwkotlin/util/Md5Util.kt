package com.hhw.hhwkotlin.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Joe.
 */
object Md5Util {

    fun encode(text: String): String {
        try {
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            val digest: ByteArray = instance.digest(text.toByteArray())
            var sb: StringBuffer = StringBuffer()
            for (b in digest) {
                var i: Int = b.toInt() and 0xff
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return text
    }

}