package top.qvisa.faceCheck.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baidu.location.BDLocation
import top.qvisa.faceCheck.utils.DateUtils
import top.qvisa.faceCheck.utils.LocationUtils

class HomeViewModel : ViewModel() {

    private val dateUtils by lazy { DateUtils() }
    private val locationUtils by lazy { LocationUtils() }
    private val _location: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val location: LiveData<String> by lazy { _location }
    val data = dateUtils.getDate()
    val weakHours = dateUtils.getWeakHour()

    fun getLocation(context: Context?) {
        locationUtils.getLocation(context, object : LocationUtils.LocationListener {
            override fun getLonLat(location: BDLocation) {
                val latitude = location.latitude.toString() //获取纬度信息
                val longitude = location.longitude.toString() //获取经度信息
                val locationStr = "$latitude $longitude"
                _location.postValue(locationStr)
            }
        })
    }

}