package com.rtc.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.rtc.data.rtcDao
import com.rtc.model.rtcHome
import com.rtc.repository.rtcRepository

class HomeViewModel (application : Application) : AndroidViewModel(application)  {

    val getAllData: MutableLiveData<List<rtcHome>>

    private val repository: rtcRepository = rtcRepository(rtcDao())

    init{
        getAllData = repository.getAllData
    }

    //Implementamos las funciones CRUD
    fun addrtc(rtc: rtcHome) {
        repository.addrtc(rtc)
    }

    fun updatertc(rtc: rtcHome) {
        repository.updatertc(rtc)
    }

    fun deletertc(rtc: rtcHome) {
       repository.deletertc(rtc)
    }
}