package com.hhw.hhwkotlin.util

import android.app.Activity
import android.content.SharedPreferences
import android.text.TextUtils
import com.hhw.hhwkotlin.MainApp
import com.hhw.hhwkotlin.bean.UserBean

/**
 * Created by Joe.
 */
object UserInfoHelper {

    val KEY_SP_NAME = "hhw_user"
    val KEY_USERNAME = "username"
    val KEY_USERID = "userid"
    val KEY_ISLOGIN = "islogin"
    val KEY_USERPSW = "userpsw"
    private var localUser = UserBean()

    fun updateUserInfo(user: UserBean): Boolean {
        val sharedPreferences: SharedPreferences = MainApp.myApp.getSharedPreferences(KEY_SP_NAME, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_USERID, user.UserId)
        editor.putString(KEY_USERNAME, user.UserName)
        editor.putString(KEY_USERPSW, user.UserPsw)
        editor.putBoolean(KEY_ISLOGIN, true)
        return editor.commit()
    }

    fun isLogin(): Boolean {
        val sharedPreferences: SharedPreferences = MainApp.myApp.getSharedPreferences(KEY_SP_NAME, Activity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_ISLOGIN, false)
    }

    fun getUserInfo(): UserBean {
        if (isLogin()) {
            initUserBean()
        }
        return localUser
    }

    fun initUserBean() {
        val sharedPreferences: SharedPreferences = MainApp.myApp.getSharedPreferences(KEY_SP_NAME, Activity.MODE_PRIVATE)
        var userId: String = sharedPreferences.getString(KEY_USERID, "")
        if (!TextUtils.isEmpty(userId)) {
            localUser = UserBean()
            localUser.UserId = sharedPreferences.getString(KEY_USERID, "")
            localUser.UserName = sharedPreferences.getString(KEY_USERNAME, "")
            localUser.UserPsw = sharedPreferences.getString(KEY_USERPSW, "")
        } else {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(KEY_ISLOGIN, true)
            editor.commit()
        }
    }

    /**
     * 清空用户信息
     */
    fun logOut() {
        localUser = UserBean()
        val sharedPreferences: SharedPreferences = MainApp.myApp.getSharedPreferences(KEY_SP_NAME, Activity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
    }
}