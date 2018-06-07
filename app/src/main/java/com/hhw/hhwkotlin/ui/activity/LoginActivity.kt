package com.hhw.hhwkotlin.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hhw.hhwkotlin.GlideApp
import com.hhw.hhwkotlin.MainActivity
import com.hhw.hhwkotlin.MainApp
import com.hhw.hhwkotlin.R
import com.hhw.hhwkotlin.bean.UserBean
import com.hhw.hhwkotlin.event.LoginEvent
import com.hhw.hhwkotlin.ui.base.BaseActivity
import com.hhw.hhwkotlin.ui.view.xlist.XListView
import com.hhw.hhwkotlin.util.Md5Util
import com.hhw.hhwkotlin.util.PhoneUtil
import com.hhw.hhwkotlin.util.UserInfoHelper
import com.joe.kotlin.util.ScreenUtils
import com.joe.kotlin.util.UIHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Joe.
 */
class LoginActivity : BaseActivity() {


    lateinit var et_phone: EditText
    lateinit var et_psw: EditText
    lateinit var ivPswVisible: ImageView
    lateinit var tv_login: TextView
    lateinit var progressDialog: ProgressDialog
    var flag = false
    var MSG_LOGIN = 1000


    var handler: Handler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                MSG_LOGIN -> {
                    //为了演示EventBus才做这样的发送 O(∩_∩)O~  会到下面的 loginEvent 接受 消息
                    EventBus.getDefault().post(LoginEvent())
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(eventLogin: LoginEvent) {
        var userBean = UserBean()
        userBean.UserId = "user_test"
        userBean.UserName = et_phone.text.toString().trim()
        //加密
        userBean.UserPsw = Md5Util.encode(et_psw.text.toString().trim())
        //更新用户信息
        UserInfoHelper.updateUserInfo(userBean)
        progressDialog.dismiss()
        UIHelper.jumpToActivity(mActivity, MainActivity().javaClass)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this)
        initView()

        et_psw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
        ivPswVisible.setOnClickListener {
            visiblePsw()
        }
        tv_login.setOnClickListener {
            login()
        }

    }

    private fun login() {
        //只是非空  最好还判断是否是电话号码
        if (TextUtils.isEmpty(et_phone.text.toString().trim())) {
            showToast("请输入电话号码")
            return
        }
        if (TextUtils.isEmpty(et_psw.text.toString().trim())) {
            showToast("请输入密码")
            return
        }
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("登陆中...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        PhoneUtil.hideInputMethod(this)
        //这里模拟 登陆 之后返回用户信息
        //延迟 2秒去主界面
        handler.sendEmptyMessageDelayed(MSG_LOGIN, 2000)
    }

    //密码可见性
    private fun visiblePsw() {
        if (!flag) {
            ivPswVisible.setImageResource(R.mipmap.psw_kejian_icon)
            et_psw.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            et_psw.transformationMethod = PasswordTransformationMethod.getInstance()
            ivPswVisible.setImageResource(R.mipmap.psw_bukejian_icon);
        }
        flag = !flag
        try {
            et_psw.setSelection(et_psw.text.toString().length)
        } catch (e: Exception) {

        }
    }

    private fun initView() {
        et_phone = findViewById(R.id.et_phone)
        et_psw = findViewById(R.id.et_psw)
        ivPswVisible = findViewById(R.id.ivPswVisible)
        tv_login = findViewById(R.id.tv_login)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}