package com.example.app_transservice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_transservice.model.TransService

class TransServiceViewModel: ViewModel() {

    var animals: List<TransService>? = null
    var ts: TransService? = null
    val currentImageURL: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val currentInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}