package com.rtc.ui.slideshow

import android.app.Application
import androidx.lifecycle.*
import com.rtc.data.rtcDao
import com.rtc.model.rtcHome
import com.rtc.repository.rtcRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SlideshowViewModel (application: Application)
    : AndroidViewModel(application){

    val getAllData: MutableLiveData<List<rtc>>
    private val repository: rtcRepository = rtcRepository(rtcDao())
    init{
        getAllData = repository.getAllData
    }

    //Implementamos las funciones CRUD
    fun addrtc(rtc: rtc) {
        viewModelScope.launch (Dispatchers.IO) { repository.addrtc(rtc) }
    }

    fun updatertc(rtc: rtc) {
        viewModelScope.launch (Dispatchers.IO) { repository.updatertc(rtc) }
    }

    fun deletertc(rtc: rtc) {
        viewModelScope.launch (Dispatchers.IO) { repository.deletertc(rtc) }
    }
}