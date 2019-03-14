package com.example.crud

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.Time
import android.util.Log
import android.view.ActionMode
import android.view.MenuItem
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.log

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        dataList.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
        gdMap.setOnClickListener{

            startActivity(Intent(this,AmapActivity::class.java))
        }
        push_config.setOnClickListener{
            startActivity(Intent(this,PushSetMain::class.java))
        }

        navigation.setOnNavigationItemReselectedListener { MenuItem->
            when(MenuItem.itemId){
                R.id.bottom1 -> startActivity(Intent(this,MainActivity::class.java))
                R.id.bottom2 -> startActivity(Intent(this,PushSetMain::class.java))
                R.id.bottom3 -> startActivity(Intent(this,AmapActivity::class.java))
            }

        }

    }

    override fun onStop() {

        super.onStop()

        Log.d("com.jinlisoft","OnDestroy")


    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        var hello_noti = NotificationCompat.Builder(this,"CURD")
            .setSmallIcon(R.drawable.notification_template_icon_low_bg)
            .setContentTitle("Hi")
            .setContentText("you have exit app ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)

        var mChannel = NotificationChannel("CURD","world",NotificationManager.IMPORTANCE_DEFAULT).apply {
            setShowBadge(true)
        }

        var mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.createNotificationChannel(mChannel)

        mNotificationManager.notify(11,hello_noti.build())
    }

}