package top.qvisa.faceCheck.utils

import android.content.Context
import com.baidu.location.*

class LocationUtils : BDAbstractLocationListener() {

    companion object {
        private var mLocationClient: LocationClient? = null
        private val option = LocationClientOption()
        private lateinit var mLocationListener: LocationListener
    }

    override fun onReceiveLocation(p0: BDLocation) {
        mLocationListener.getLonLat(p0)
    }

    fun getLocation(context: Context?, locationListener: LocationListener) {
        mLocationClient = LocationClient(context)
        mLocationClient?.registerLocationListener(this)
        setLocationOption()
        mLocationClient?.locOption = option
        mLocationListener = locationListener
        mLocationClient?.start()
    }

    interface LocationListener {
        fun getLonLat(location: BDLocation)
    }

    private fun setLocationOption() {
        option.setCoorType("bd09ll")
        option.setScanSpan(0)
        option.isOpenGps = true
        option.setIgnoreKillProcess(false)
        option.setWifiCacheTimeOut(5 * 60 * 1000)
    }
}