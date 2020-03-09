package top.qvisa.faceCheck.utils

import java.util.*

class DateUtils {
    private val calender = Calendar.getInstance()
    fun getDate(): String {
        calender.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val mYear = calender[Calendar.YEAR]
        val mMonth = calender[Calendar.MONTH] + 1
        val mDay = calender[Calendar.DAY_OF_MONTH]
        return "$mYear/$mMonth/$mDay"
    }

    fun getWeakHour(): String {
        calender.timeZone = TimeZone.getTimeZone("GMT+8:00")
        val mWeak = when (calender[Calendar.DAY_OF_WEEK]) {
            1 -> "天"
            2 -> "一"
            3 -> "二"
            4 -> "三"
            5 -> "四"
            6 -> "五"
            7 -> "六"
            else -> ""
        }
        val mHour = calender[Calendar.HOUR_OF_DAY]
        return "星期${mWeak} ${mHour}时"
    }
}