package com.example.crud

import android.app.Activity
import android.os.Bundle
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.MyLocationStyle
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map.view.*

class AmapActivity : Activity() {
    internal var mMapView : MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mMapView = findViewById(R.id.map) as MapView
        mMapView!!.onCreate(savedInstanceState)
        var aMap : AMap? = null
        if(aMap == null){
            aMap = map.map
        }
       var myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000)
        aMap!!.setMyLocationStyle(myLocationStyle)
        aMap.isMyLocationEnabled = true


    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mMapView!!.onSaveInstanceState(outState)
    }
}