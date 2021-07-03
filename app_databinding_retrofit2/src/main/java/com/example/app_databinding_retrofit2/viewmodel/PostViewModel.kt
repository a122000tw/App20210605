package com.example.app_databinding_retrofit2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_databinding_retrofit2.model.Post

class PostViewModel: ViewModel() {

    var posts = MutableLiveData<MutableList<Post>>()

    init {
        posts.value = ArrayList()
    }


}