package com.rtc.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rtc.data.rtcDao
import com.rtc.model.rtcHome

class rtcRepository (private val rtcDao: rtcDao){

    val getAllData: MutableLiveData<List<rtcHome>> = rtcDao.getrtc()

    fun addrtc(rtc: rtcHome) {
        rtcDao.savertc(rtc)
    }

    fun updatertc(rtc: rtcHome) {
        rtcDao.savertc(rtc)
    }

    fun deletertc(rtc: rtcHome) {
        rtcDao.deletertc(rtc)
    }

}