package com.example.crud

import android.content.Intent
import android.content.BroadcastReceiver
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import cn.jpush.android.api.JPushInterface
import android.widget.*
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_push_main.*


class PushSetMain : Activity(), View.OnClickListener {

    private var mInit: Button? = null
    private var mSetting: Button? = null
    private var mStopPush: Button? = null
    private var mResumePush: Button? = null
    private var mGetRid: Button? = null
    private var mRegId: TextView? = null
    private var msgText: EditText? = null


    //for receive customer msg from jpush server
    private var mMessageReceiver: MessageReceiver? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.crud.R.layout.activity_push_main)
        initView()
        registerMessageReceiver()  // used for receive msg
    }

    private fun initView() {
        val mImei = tv_imei



        val mAppKey = tv_appkey
        var appKey = ExampleUtil.getAppKey(applicationContext)
        if (null == appKey) appKey = "AppKey异常"
        mAppKey.text = "AppKey: " + appKey!!

        mRegId = tv_regId
        mRegId!!.text = "RegId:"

        val packageName = packageName
        val mPackage = tv_package
        mPackage.text = "PackageName: $packageName"

        val deviceId = ExampleUtil.getDeviceId(applicationContext)
        val mDeviceId = tv_device_id
        mDeviceId.text = "deviceId:$deviceId"

        val versionName = ExampleUtil.GetVersion(applicationContext)
        val mVersion = tv_version
        mVersion.text = "Version: $versionName"

        mInit = init
        mInit!!.setOnClickListener(this)

        mStopPush = stopPush
        mStopPush!!.setOnClickListener(this)

        mResumePush = resumePush
        mResumePush!!.setOnClickListener(this)

        mGetRid = getRegistrationId
        mGetRid!!.setOnClickListener(this)

        mSetting = setting
        mSetting!!.setOnClickListener(this)

        msgText = msg_rec
    }


    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.init -> init()

            R.id.stopPush -> JPushInterface.stopPush(applicationContext)
            R.id.resumePush -> JPushInterface.resumePush(applicationContext)
            R.id.getRegistrationId -> {
                val rid = JPushInterface.getRegistrationID(applicationContext)
                if (!rid.isEmpty()) {
                    mRegId!!.text = "RegId:$rid"
                } else {
                    Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private fun init() {
        JPushInterface.init(applicationContext)
    }


    override fun onResume() {
        isForeground = true
        super.onResume()
    }


    override fun onPause() {
        isForeground = false
        super.onPause()
    }


    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver!!)
        super.onDestroy()
    }

    fun registerMessageReceiver() {
        mMessageReceiver = MessageReceiver()
        val filter = IntentFilter()
        filter.priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        filter.addAction(MESSAGE_RECEIVED_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver!!, filter)
    }

    inner class MessageReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION == intent.action) {
                    val messge = intent.getStringExtra(KEY_MESSAGE)
                    val extras = intent.getStringExtra(KEY_EXTRAS)
                    val showMsg = StringBuilder()
                    showMsg.append("$KEY_MESSAGE : $messge\n")
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append("$KEY_EXTRAS : $extras\n")
                    }
                    setCostomMsg(showMsg.toString())
                }
            } catch (e: Exception) {
            }

        }
    }

    private fun setCostomMsg(msg: String) {
        if (null != msgText) {
            msgText!!.setText(msg)
            msgText!!.visibility = android.view.View.VISIBLE
        }
    }

    companion object {

        var isForeground = false
        val MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION"
        val KEY_TITLE = "title"
        val KEY_MESSAGE = "message"
        val KEY_EXTRAS = "extras"
    }

}