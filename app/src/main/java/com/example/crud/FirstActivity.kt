package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_first.*

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
    }

}