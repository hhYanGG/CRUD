package com.example.crud

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.MyLocationStyle
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.activity_map.view.*
import java.text.SimpleDateFormat
import java.util.*

class AmapActivity : Activity() ,AMapLocationListener{
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
        location()


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

    override fun onLocationChanged(p0: AMapLocation?) {
        if(p0 != null){
            if(p0.errorCode == 0){
                p0.locationType
                p0.latitude
                p0.longitude
                p0.accuracy
                var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var date = Date(p0.time)
                df.format(date)
            }else{
                Log.e("AmapError","locationeError, ErrorCode: ${p0.errorCode} ${p0.errorInfo}"
                )
            }
        }
    }

    private fun location(){
        var mLocationClient = AMapLocationClient(this)
        var mLocationOption = AMapLocationClientOption()

        mLocationClient.setLocationListener(this)

        mLocationOption!!.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors)
        mLocationOption.setInterval(2000)
        mLocationClient.setLocationOption(mLocationOption)
        mLocationClient.startLocation()

    }


}