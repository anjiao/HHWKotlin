package com.hhw.hhwkotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hhw.hhwkotlin.MainActivity
import com.hhw.hhwkotlin.R
import com.hhw.hhwkotlin.util.UserInfoHelper
import com.joe.kotlin.util.UIHelper

/**
 * Created by Joe.
 */
class SplashActivity : AppCompatActivity() {

    val sleepTime: Long = 2500

    override fun onStart() {
        super.onStart()
        Thread(Runnable {
            Thread.sleep(sleepTime)
            runOnUiThread(Runnable { gotoLogin() })
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun gotoLogin() {
        if (UserInfoHelper.isLogin()) {
            UIHelper.jumpToActivity(this, MainActivity().javaClass)
        } else {
            UIHelper.jumpToActivity(this, LoginActivity().javaClass)
        }
        finish()
    }
}