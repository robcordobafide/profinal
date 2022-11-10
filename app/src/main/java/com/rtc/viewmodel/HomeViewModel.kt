package com.rtc.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.rtc.data.rtcDao
import com.rtc.model.rtcHome
import com.rtc.repository.rtcRepository

class HomeViewModel (application : Application) : AndroidViewModel(application)  {

    val getAllData: MutableLiveData<List<rtc>>

    private val repository: rtcRepository = rtcRepository(rtcDao())

    init{
        getAllData = repository.getAllData
    }

    //Implementamos las funciones CRUD
    fun addrtc(rtc: rtc) {
        repository.addrtc(rtc)
    }

    fun updatertc(rtc: rtc) {
        repository.updatertc(rtc)
    }

    fun deletertc(rtc: rtc) {
       repository.deletertc(rtc)
    }
}